package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GravarCertificadoWS extends Comunicado {
    private String nomeAluno;
    private String curso;
    private String dataConclusao;
    private Integer cargaHoraria;
}