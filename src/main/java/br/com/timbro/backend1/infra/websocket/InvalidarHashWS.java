package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvalidarHashWS extends Comunicado {
    private String hash;
    private String idRequisicao; // Precisa estar alinhado com o backend 2
}