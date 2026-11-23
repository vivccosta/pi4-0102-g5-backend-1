package br.com.timbro.backend1.infra.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GravarCertificadoWS extends Comunicado {
    private String evento = "gravarCertificado";
    private String nomeAluno;
    private String curso;
    private String dataConclusao;
    private Integer cargaHoraria;
    private String idRequisicao;

    public GravarCertificadoWS(String nomeAluno, String curso, String dataConclusao, Integer cargaHoraria, String idRequisicao) {
        this.evento = "gravarCertificado";
        this.nomeAluno = nomeAluno;
        this.curso = curso;
        this.dataConclusao = dataConclusao;
        this.cargaHoraria = cargaHoraria;
        this.idRequisicao = idRequisicao;
    }
}