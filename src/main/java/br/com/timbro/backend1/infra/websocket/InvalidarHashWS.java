package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvalidarHashWS extends Comunicado {
    private String evento = "invalidarHash";
    private String hash;
    private String idRequisicao;

    public InvalidarHashWS(String hash, String idRequisicao) {
        this.evento = "invalidarHash";
        this.hash = hash;
        this.idRequisicao = idRequisicao;
    }
}