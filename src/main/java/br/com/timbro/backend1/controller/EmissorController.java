package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Certificado;
import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.dto.CertificadoResumoDTO;
import br.com.timbro.backend1.repository.CertificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/emissores")
public class EmissorController {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    // CT15: Retorna o histórico de certificados do emissor logado
    @GetMapping("/{emissorId}/certificados")
    public ResponseEntity<List<CertificadoResumoDTO>> listarCertificados(@PathVariable Integer emissorId) {
        
        // 1. Descobre quem é o usuário por trás do Token JWT atual
        Emissor emissorLogado = (Emissor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. Onde certificamos a segurança:
        // Verifica se o ID da URL pertence ao dono do Token
        if (!emissorLogado.getEmissorId().equals(emissorId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, 
                    "Acesso negado. Você só pode visualizar o seu próprio histórico."
            );
        }

        // 3. Busca no MongoDB todos os certificados vinculados a este Emissor (CT16)
        List<Certificado> certificados = certificadoRepository.findByEmissorId(emissorId);

        // 4. Converte a lista de "Entidades do Banco" para a lista de "DTOs do Frontend"
        List<CertificadoResumoDTO> resposta = certificados.stream().map(cert -> 
                new CertificadoResumoDTO(
                        cert.getCertificadoId(),
                        cert.getNomeAluno(),
                        cert.getHash(),
                        cert.getDataEmissao() != null ? cert.getDataEmissao().toString() : "",
                        cert.getStatus().name() // Retorna "VALIDO" ou "INVALIDO"
                )
        ).collect(Collectors.toList());

        // 5. Retorna a lista completa com Status 200 
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarEmissor(@RequestBody @Valid br.com.timbro.backend1.dto.CadastroEmissorRequestDTO request) {
        
        // Verifica se o email ou CNPJ já existem no banco para evitar duplicidade
        if (emissorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
        }

        Emissor novoEmissor = new Emissor();
        novoEmissor.setNome(request.getNome());
        novoEmissor.setEmail(request.getEmail());
        novoEmissor.setCnpjCpf(request.getCnpjCpf());
        
        // Criptografa a senha antes de salvar no banco
        novoEmissor.setSenha(passwordEncoder.encode(request.getSenha()));
        
        novoEmissor.setPerfil("EMISSOR");
        novoEmissor.setStatus(br.com.timbro.backend1.domain.StatusEmissor.PENDENTE); // Fica aguardando o Admin

        emissorRepository.save(novoEmissor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso. Aguardando aprovação do administrador.");
    }
}