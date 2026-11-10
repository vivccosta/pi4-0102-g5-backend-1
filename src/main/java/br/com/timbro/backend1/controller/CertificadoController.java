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
}