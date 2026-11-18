package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.domain.StatusEmissor;
import br.com.timbro.backend1.dto.LoginRequestDTO;
import br.com.timbro.backend1.dto.LoginResponseDTO;
import br.com.timbro.backend1.infra.security.TokenService;
import br.com.timbro.backend1.repository.EmissorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmissorRepository emissorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> autenticarEmissor(@RequestBody @Valid LoginRequestDTO request) {
        
        // 1. Busca o usuário no banco de dados pelo email (CT2)
        Optional<Emissor> emissorOpt = emissorRepository.findByEmail(request.getEmail());

        // 2. Valida se o emissor existe e se a senha enviada bate com o hash BCrypt salvo no banco
        if (emissorOpt.isEmpty() || !passwordEncoder.matches(request.getSenha(), emissorOpt.get().getSenha())) {
            // Lança o erro 401 que será capturado pelo seu TratadorDeErros (Fluxo E1)
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, 
                    "Acesso não autorizado. Apenas emissores cadastrados podem acessar esta área."
            );
        }

        Emissor emissor = emissorOpt.get();

        // 3. Verifica se o emissor já foi aprovado pelo Administrador (Fluxo E5)
        if (emissor.getStatus() != StatusEmissor.ATIVO) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, 
                    "Acesso não autorizado. O cadastro do emissor ainda está pendente."
            );
        }

        // 4. Gera o token e monta a resposta de sucesso
        String token = tokenService.gerarToken(emissor);
        
        // Se o perfil for nulo no banco, assume EMISSOR como padrão, conforme CT1
        String perfil = emissor.getPerfil() != null ? emissor.getPerfil() : "EMISSOR"; 

        LoginResponseDTO response = new LoginResponseDTO(token, emissor.getNome(), perfil);

        return ResponseEntity.ok(response);
    }
}