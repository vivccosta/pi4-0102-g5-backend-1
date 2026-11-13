package br.com.timbro.backend1.infra.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetornoConsultaWS extends Comunicado {
    private String evento;
    private boolean status;
    private boolean statusConsulta;
    private boolean existe;
    private boolean encontrado;
    private String erro;
    private String idRequisicao;
}