package br.com.timbro.backend1.infra.websocket;

import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Iterator;
import java.util.Map;

public class Parceiro {

    private final WebSocketSession sessao;
    private final ObjectMapper mapeadorJson;
    private final Object mutEx = new Object();

    // Fila que simula a espera da rede
    private final BlockingQueue<Comunicado> mensagensRecebidas = new LinkedBlockingQueue<>();

    public Parceiro(WebSocketSession sessao, ObjectMapper mapeadorJson) throws Exception {

        if (sessao == null) {
            throw new Exception("A sessão web não foi iniciada");
        }
        if (mapeadorJson == null) {
            throw new Exception("Mapeador de objetos não foi recebido");
        }
        this.sessao = sessao;
        this.mapeadorJson = mapeadorJson;
    }

    // Pega o DTO do Java, vira JSON e manda pro Python
    public void envie(Comunicado x) throws Exception {
        String json = mapeadorJson.writeValueAsString(x);
        
        // Exclusão Mútua: Apenas uma thread envia por vez
        synchronized (mutEx) {
            sessao.sendMessage(new TextMessage(json));
        }
    }

    // A thread espiã fica travada aqui até chegar algo na fila
    public Comunicado espie() throws Exception {
        return mensagensRecebidas.peek(); // Só olha quem é o primeiro da fila
    }

    public void receba() throws Exception {
        mensagensRecebidas.take(); // Remove o primeiro da fila
    }

    public void adeus() throws Exception {
        if (this.sessao != null && this.sessao.isOpen()) {
            this.sessao.close();
        }
    }

    // O Spring WebSocket vai chamar esse método quando o Python responder
    public void adicionarMensagemDoPython(String jsonTexto) throws Exception {
        
        // 1. Lê a String JSON como uma "árvore"
        com.fasterxml.jackson.databind.JsonNode noh = mapeadorJson.readTree(jsonTexto);
        
        // 2. Verifica se a mensagem recebida tem a etiqueta "evento"
        if (noh.has("evento")) {
            String evento = noh.get("evento").asText();
            Comunicado comunicadoConvertido = null;

            // 3. Usa a etiqueta do evento para rotear para o DTO correto
            switch (evento) {
                case "retornoGravacaoBlockchain": 
                    comunicadoConvertido = mapeadorJson.treeToValue(noh, RetornoGravacaoWS.class);
                    break;
                    
                case "retornoConsultaBlockchain": 
                    comunicadoConvertido = mapeadorJson.treeToValue(noh, RetornoConsultaWS.class);
                    break;
                    
                case "retornoInvalidacaoBlockchain": 
                    comunicadoConvertido = mapeadorJson.treeToValue(noh, RetornoInvalidacaoWS.class);
                    break;
                    
                default:
                    System.out.println("Aviso: Evento desconhecido recebido do Python: " + evento);
                    break;
            }

            // 4. Se a conversão deu certo, coloca na fila!
            // Isso faz acordar a TratadoraDeMensagensWS que estava travada no método espie()
            if (comunicadoConvertido != null) {
                mensagensRecebidas.put(comunicadoConvertido);
            }
        } else {
            System.out.println("Aviso: JSON recebido sem o campo 'evento'. Ignorando...");
        }
    }
}