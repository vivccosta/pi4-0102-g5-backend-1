package br.com.timbro.backend1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevogarCertificadoResponseDTO {
    private boolean sucesso;
    private String mensagem;
}