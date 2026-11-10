package br.com.timbro.backend1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidacaoCertificadoResponseDTO {
    private boolean valido;
    private String mensagem;
    private String nomeAluno;
    private String curso;
    private String dataConclusao;
    private String hash;
}