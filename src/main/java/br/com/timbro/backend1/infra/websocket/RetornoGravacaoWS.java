package br.com.timbro.backend1.infra.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetornoGravacaoWS extends Comunicado {
    private String hashTransacao;
    private boolean status;
}