package br.com.timbro.backend1.infra.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetornoInvalidacaoWS extends Comunicado {
    private boolean sucesso;
    private String hashRevogado;
    private String idRequisicao;
}