package br.com.timbro.backend1.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroResposta {
    private Integer status;
    private String mensagem;
}