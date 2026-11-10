package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Certificado;
import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.domain.StatusCertificado;
import br.com.timbro.backend1.dto.RegistroCertificadoRequestDTO;
import br.com.timbro.backend1.dto.RegistroCertificadoResponseDTO;
import br.com.timbro.backend1.infra.websocket.Comunicado;
import br.com.timbro.backend1.infra.websocket.GerenciadorDeCorrelacao;
import br.com.timbro.backend1.infra.websocket.GravarCertificadoWS;
import br.com.timbro.backend1.infra.websocket.Parceiro;
import br.com.timbro.backend1.infra.websocket.RetornoGravacaoWS;
import br.com.timbro.backend1.repository.CertificadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/certificados")
public class CertificadoController {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Autowired
    private Parceiro parceiroBlockchain;

    @Autowired
    private GerenciadorDeCorrelacao correlacao;

    @Autowired 
    private br.com.timbro.backend1.service.HashService hashService;

    @PostMapping("/registrar")
    public ResponseEntity<RegistroCertificadoResponseDTO> registrarCertificado(
            @RequestBody @Valid RegistroCertificadoRequestDTO request) {
        
        // 1. Pega o emissor que foi autenticado pelo filtro JWT
        Emissor emissorLogado = (Emissor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. Prepara o DTO para enviar via WebSocket (CT5)
        GravarCertificadoWS pedidoWS = new GravarCertificadoWS(
                request.getNomeAluno(),
                request.getCurso(),
                request.getDataConclusao(),
                request.getCargaHoraria()
        );

        // Criamos um ID único para esta requisição para que a Tratadora saiba quem acordar
        String idRequisicao = UUID.randomUUID().toString();

        // 3. Registra a espera no Guarda-Volumes
        CompletableFuture<Comunicado> futuro = correlacao.registrarEspera(idRequisicao);

        try {
            // 4. Envia para a Blockchain (CT5)
            parceiroBlockchain.envie(pedidoWS);

            // 5. Trava a thread HTTP aguardando a resposta do Python por no máximo 10 segundos
            Comunicado resposta = futuro.get(10, TimeUnit.SECONDS);

            if (resposta instanceof RetornoGravacaoWS retornoWS) {
                
                if (!retornoWS.isStatus()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A blockchain rejeitou a gravação.");
                }

                // 6. Monta o certificado para salvar no banco (CT7 / CT16)
                Certificado novoCertificado = new Certificado();
                novoCertificado.setEmissorId(emissorLogado.getEmissorId());
                novoCertificado.setNomeAluno(request.getNomeAluno());
                novoCertificado.setCurso(request.getCurso());
                novoCertificado.setCargaHoraria(request.getCargaHoraria());
                novoCertificado.setDataConclusao(LocalDate.parse(request.getDataConclusao()));
                novoCertificado.setHash(retornoWS.getHashTransacao());
                novoCertificado.setStatus(StatusCertificado.VALIDO);
                novoCertificado.setDataEmissao(LocalDateTime.now());

                // 7. Persiste no MongoDB (CT7)
                try {
                    certificadoRepository.save(novoCertificado);
                } catch (Exception e) {
                    // Regra H01.1: Se a cadeia grava e o banco falha, nunca responde sucesso
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                            "Falha ao persistir no banco de dados após gravação na blockchain.");
                }

                // 8. Responde ao Frontend (CT8)
                RegistroCertificadoResponseDTO response = new RegistroCertificadoResponseDTO(
                        true, 
                        "Registro realizado com sucesso!", 
                        retornoWS.getHashTransacao()
                );
                
                return ResponseEntity.ok(response);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resposta inesperada da Blockchain");
            }

        } catch (Exception e) {
            // Remove a espera em caso de falha ou timeout
            correlacao.liberarEspera(idRequisicao, null);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de comunicação com a Blockchain: " + e.getMessage());
        }
    }

    // Rota pública (foi liberada no SecurityConfig)
    @PostMapping(value = "/validar", consumes = {"multipart/form-data"})
    public ResponseEntity<ValidacaoCertificadoResponseDTO> validarCertificado(
            @RequestParam("tipoValidacao") String tipoValidacao,
            @RequestParam(value = "arquivoPdf", required = false) org.springframework.web.multipart.MultipartFile arquivoPdf,
            @RequestParam(value = "chaveAutenticacao", required = false) String chaveAutenticacao) throws Exception {

        if (tipoValidacao == null || tipoValidacao.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo de validação é obrigatório.");
        }

        // CENÁRIO A: Validação via PDF (CT9, CT10, CT11)
        if (tipoValidacao.equalsIgnoreCase("PDF")) {
            if (arquivoPdf == null || arquivoPdf.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo PDF não fornecido.");
            }

            // 1. Gera o hash SHA-256 do arquivo no próprio Java
            String hashGerado = hashService.gerarHashSha256(arquivoPdf);

            // 2. Prepara o pedido WS (lembre-se do idRequisicao alinhado com o Caio!)
            String idRequisicao = UUID.randomUUID().toString();
            ConsultarHashWS consultaWS = new ConsultarHashWS(hashGerado); 
            // *Nota: Assumindo que você adicionou o getIdRequisicao() e setIdRequisicao() no ConsultarHashWS

            CompletableFuture<Comunicado> futuro = correlacao.registrarEspera(idRequisicao);
            parceiroBlockchain.envie(consultaWS);

            // 3. Aguarda resposta da Blockchain
            Comunicado resposta = futuro.get(10, TimeUnit.SECONDS);

            if (resposta instanceof RetornoConsultaWS retornoWS) {
                if (retornoWS.isEncontrado()) {
                    // Busca os metadados no banco para enriquecer a resposta (nome do aluno, curso, etc)
                    var certificadoOpt = certificadoRepository.findByHash(hashGerado);
                    
                    if (certificadoOpt.isPresent()) {
                        Certificado cert = certificadoOpt.get();
                        // Verifica se não foi revogado
                        if (cert.getStatus() == StatusCertificado.INVALIDO) {
                            return responderInvalido("Certificado revogado.");
                        }
                        return responderValido(cert);
                    }
                    return responderValidoBasico(hashGerado); // Existe na blockchain, mas sumiu do nosso banco
                } else {
                    return responderInvalido("O documento fornecido não possui registro autêntico.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na consulta à Blockchain.");
            }
        } 
        
        // CENÁRIO B: Validação via Número de Série (CT12, CT13)
        else if (tipoValidacao.equalsIgnoreCase("CHAVE")) {
            if (chaveAutenticacao == null || chaveAutenticacao.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chave de autenticação não fornecida.");
            }

            // Busca direto no banco de dados (CT12)
            var certificadoOpt = certificadoRepository.findByHash(chaveAutenticacao);

            if (certificadoOpt.isPresent()) {
                Certificado cert = certificadoOpt.get();
                if (cert.getStatus() == StatusCertificado.INVALIDO) {
                    return responderInvalido("Certificado revogado.");
                }
                return responderValido(cert);
            } else {
                return responderInvalido("Chave de autenticação desconhecida.");
            }
        } 
        
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de validação inválido.");
        }
    }

    // Métodos auxiliares para montar o DTO de resposta limpo (CT14)
    private ResponseEntity<ValidacaoCertificadoResponseDTO> responderValido(Certificado cert) {
        return ResponseEntity.ok(ValidacaoCertificadoResponseDTO.builder()
                .valido(true)
                .mensagem("Certificado válido")
                .nomeAluno(cert.getNomeAluno())
                .curso(cert.getCurso())
                .dataConclusao(cert.getDataConclusao().toString())
                .hash(cert.getHash())
                .build());
    }

    private ResponseEntity<ValidacaoCertificadoResponseDTO> responderValidoBasico(String hash) {
        return ResponseEntity.ok(ValidacaoCertificadoResponseDTO.builder()
                .valido(true)
                .mensagem("Certificado válido na Blockchain (sem metadados locais)")
                .hash(hash)
                .build());
    }

    private ResponseEntity<ValidacaoCertificadoResponseDTO> responderInvalido(String motivo) {
        return ResponseEntity.ok(ValidacaoCertificadoResponseDTO.builder()
                .valido(false)
                .mensagem("Certificado inválido: " + motivo)
                .build());
    }
}