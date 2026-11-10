package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.domain.StatusEmissor;
import br.com.timbro.backend1.dto.AlterarStatusEmissorRequestDTO;
import br.com.timbro.backend1.dto.EmissorResumoAdminDTO;
import br.com.timbro.backend1.repository.EmissorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/emissores")
public class AdminController {

    @Autowired
    private EmissorRepository emissorRepository;

    // 1. Lista todos os emissores
    @GetMapping("/pendentes")
    public ResponseEntity<List<EmissorResumoAdminDTO>> listarEmissoresPendentes() {
        
        // Busca apenas os que estão aguardando aprovação
        List<Emissor> pendentes = emissorRepository.findByStatus(StatusEmissor.PENDENTE);

        List<EmissorResumoAdminDTO> resposta = pendentes.stream().map(emissor ->
                new EmissorResumoAdminDTO(
                        emissor.getEmissorId(),
                        emissor.getNome(),
                        emissor.getEmail(),
                        emissor.getCnpj(),
                        emissor.getStatus().name()
                )
        ).collect(Collectors.toList());

        return ResponseEntity.ok(resposta);
    }

    // 2. Altera o status de um emissor (Aprova ou Bloqueia)
    @PutMapping("/{emissorId}/status")
    public ResponseEntity<String> alterarStatus(
            @PathVariable Integer emissorId,
            @RequestBody @Valid AlterarStatusEmissorRequestDTO request) {

        var emissorOpt = emissorRepository.findById(emissorId);
        
        if (emissorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Emissor não encontrado.");
        }

        Emissor emissor = emissorOpt.get();

        try {
            // Converte a string que veio do DTO para o Enum do Java
            StatusEmissor novoStatus = StatusEmissor.valueOf(request.getNovoStatus().toUpperCase());
            emissor.setStatus(novoStatus);
            emissorRepository.save(emissor);
            
            return ResponseEntity.ok("Status do emissor atualizado para " + novoStatus.name() + " com sucesso.");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido. Use ATIVO, PENDENTE ou BLOQUEADO.");
        }
    }
}