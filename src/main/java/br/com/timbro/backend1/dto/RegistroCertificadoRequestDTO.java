package br.com.timbro.backend1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroCertificadoRequestDTO {
    @NotBlank(message = "O nome do aluno é obrigatório")
    private String nomeAluno;
    
    @NotBlank(message = "O curso é obrigatório")
    private String curso;
    
    @NotNull(message = "A carga horária é obrigatória")
    private Integer cargaHoraria;
    
    @NotBlank(message = "A data de conclusão é obrigatória")
    private String dataConclusao; // É string pois ficha CT4 pede "texto" no formato "2026-06-30"

    // Embora o token já venha no Header pelo SecurityFilter, 
    // mantemos aqui para corresponder ao contrato do CT4
    private String tokenEmissor; 
}