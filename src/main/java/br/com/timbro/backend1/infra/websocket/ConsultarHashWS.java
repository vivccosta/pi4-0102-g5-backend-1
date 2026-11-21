package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConsultarHashWS extends Comunicado {
    private String hash;
    private String idRequisicao;

    public ConsultarHashWS(String hash) {
        this.hash = hash;
    }
}