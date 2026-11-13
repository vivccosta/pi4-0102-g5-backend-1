package br.com.timbro.backend1.infra.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetornoGravacaoWS extends Comunicado {
    private String evento;
    private boolean status;
    private String hash;
    private String hashTransacao;
    private Integer bloco;
    private String timestamp;
    private String idRequisicao;
}