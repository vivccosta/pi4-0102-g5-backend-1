package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ConsultarHashWS extends Comunicado {
    private String hash;
}