package br.com.timbro.backend1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificadoResumoDTO {
    private Integer certificadoId;
    private String nomeAluno;
    private String hash;
    private String dataEmissao; 
    private String status;
}