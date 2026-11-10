package br.com.timbro.backend1.infra.websocket;

import br.com.timbro.backend1.infra.websocket.Comunicado;

// Criação de uma thread dedicada a escutar
public class TratadoraDeMensagensWS extends Thread {

    private Parceiro parceiroPython;
    private GerenciadorDeCorrelacao correlacao;

    public TratadoraDeMensagensWS(Parceiro parceiroPython, GerenciadorDeCorrelacao correlacao) {
        this.parceiroPython = parceiroPython;
        this.correlacao = correlacao;
    }

    @Override
    public void run() {
        try {
            // O loop infinito para espionar a rede
            while (true) {
                // O codigo fica travado aqui até o Python (backend 2) mandar um JSON
                Comunicado comunicado = parceiroPython.espie(); 

                if (comunicado instanceof RetornoGravacaoWS) {
                    RetornoGravacaoWS retorno = (RetornoGravacaoWS) comunicado;
                    // Avisa o GerenciadorDeCorrelacao que a resposta chegou!
                    correlacao.liberarEspera(retorno.getHashTransacao(), retorno);
                    parceiroPython.receba(); // Limpa o buffer da rede
                } 
                else if (comunicado instanceof RetornoConsultaWS) {
                    RetornoConsultaWS retorno = (RetornoConsultaWS) comunicado;
                    // Usa o próprio hash como ID para liberar
                    correlacao.liberarEspera(String.valueOf(retorno.getBloco()), retorno);
                    parceiroPython.receba();
                }
                else if(comunicado instanceof RetornoInvalidacaoWS) {
                    RetornoInvalidacaoWS retorno = (RetornoInvalidacaoWS) comunicado;

                    // Usa o idRequisicao para acordar a thread exata do Controller HTTP
                    correlacao.liberarEspera(retorno.getIdRequisicao(), retorno);

                    parceiroPython.receba(); // Limpa o buffer
                }
                else if (comunicado instanceof ComunicadoDeDesligamento) {
                    break; // Se o servidor caiu, sai do loop
                }
            }
        } catch (Exception erro) {
            // Queda de conexão
        }
    }
}