package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConsultarHashWS extends Comunicado {
    private String evento = "consultarHash";
    private String hash;
    private String idRequisicao;

    public ConsultarHashWS(String hash) {
        this.evento = "consultarHash";
        this.hash = hash;
    }

    public ConsultarHashWS(String hash, String idRequisicao) {
        this.evento = "consultarHash";
        this.hash = hash;
        this.idRequisicao = idRequisicao;
    }
}