package br.com.timbro.backend1.infra.websocket;

//import br.com.timbro.backend1.infra.websocket.Comunicado;

// Criação de uma thread dedicada a escutar
public class TratadoraDeMensagensWS extends Thread {

    private Parceiro parceiroPython;
    private GerenciadorDeCorrelacao correlacao;

    public TratadoraDeMensagensWS(Parceiro parceiroPython, GerenciadorDeCorrelacao correlacao) {
        super("TratadoraDeMensagensWS");
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
                    // Avisa o GerenciadorDeCorrelacao que a resposta chegou pelo idRequisicao!
                    String id = (retorno.getIdRequisicao() != null && !retorno.getIdRequisicao().isBlank()) 
                            ? retorno.getIdRequisicao() 
                            : retorno.getHashTransacao();
                    correlacao.liberarEspera(id, retorno);
                    parceiroPython.receba(); // Limpa o buffer da rede
                } 
                else if (comunicado instanceof RetornoConsultaWS) {
                    RetornoConsultaWS retorno = (RetornoConsultaWS) comunicado;
                    // Usa o idRequisicao (ou o hash como fallback) para liberar a Thread do Controller
                    String id = (retorno.getIdRequisicao() != null && !retorno.getIdRequisicao().isBlank())
                            ? retorno.getIdRequisicao()
                            : retorno.getHash();
                    correlacao.liberarEspera(id, retorno);
                    parceiroPython.receba();
                }
                else if (comunicado instanceof RetornoInvalidacaoWS) {
                    RetornoInvalidacaoWS retorno = (RetornoInvalidacaoWS) comunicado;

                    // Usa o idRequisicao para acordar a thread exata do Controller HTTP
                    String id = (retorno.getIdRequisicao() != null && !retorno.getIdRequisicao().isBlank())
                            ? retorno.getIdRequisicao()
                            : retorno.getHashRevogado();
                    correlacao.liberarEspera(id, retorno);

                    parceiroPython.receba(); // Limpa o buffer
                }
                else if (comunicado instanceof ComunicadoDeDesligamento) {
                    break; // Se o servidor caiu, sai do loop
                }
                else {
                    parceiroPython.receba();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("[WS] TratadoraDeMensagensWS finalizada.");
        } catch (Exception erro) {
            System.err.println("[WS] Queda de conexão na TratadoraDeMensagensWS: " + erro.getMessage());
        }
    }
}