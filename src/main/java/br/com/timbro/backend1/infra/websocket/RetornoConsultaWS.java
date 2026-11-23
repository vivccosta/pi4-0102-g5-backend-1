package br.com.timbro.backend1.infra.websocket;

import com.fasterxml.jackson.databind.JsonNode;
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
    private String hash;
    private Integer bloco;
    private String timestamp;
    private JsonNode dados;
    private String hashBloco;
    private String hashAnterior;
    private String erro;
    private String mensagem;
    private String idRequisicao;
}