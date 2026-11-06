package br.com.timbro.backend1.infra.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetornoConsultaWS extends Comunicado {
    private boolean statusConsulta;
    private boolean encontrado;
    private Integer bloco;
}