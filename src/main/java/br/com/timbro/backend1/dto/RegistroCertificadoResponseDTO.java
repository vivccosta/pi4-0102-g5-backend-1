package br.com.timbro.backend1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistroCertificadoResponseDTO {
    private boolean sucesso;
    private String mensagem;
    private String hash;
}