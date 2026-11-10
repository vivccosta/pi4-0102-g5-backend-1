package br.com.timbro.backend1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlterarStatusEmissorRequestDTO {
    @NotBlank(message = "O novo status é obrigatório (ATIVO ou BLOQUEADO)")
    private String novoStatus;
}