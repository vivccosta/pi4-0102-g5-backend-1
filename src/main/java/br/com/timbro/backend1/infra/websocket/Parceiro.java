package br.com.timbro.backend1.infra.websocket;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Parceiro {

    private WebSocketSession sessao;
    private final ObjectMapper mapeadorJson;
    private final Object mutEx = new Object();

    // Fila que armazena mensagens recebidas da rede
    private final BlockingQueue<Comunicado> mensagensRecebidas = new LinkedBlockingQueue<>();

    // Construtor que o Spring vai usar ao subir o servidor
    @Autowired
    public Parceiro() {
        this.mapeadorJson = new ObjectMapper();
        this.mapeadorJson.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Método para injetar a sessão quando o WebSocket conectar
    public void setSessao(WebSocketSession sessao) {
        this.sessao = sessao;
    }

    public Parceiro(WebSocketSession sessao, ObjectMapper mapeadorJson) throws Exception {
        if (sessao == null) {
            throw new Exception("A sessão web não foi iniciada");
        }
        if (mapeadorJson == null) {
            throw new Exception("Mapeador de objetos não foi recebido");
        }
        this.sessao = sessao;
        this.mapeadorJson = mapeadorJson;
        this.mapeadorJson.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Pega o DTO do Java, vira JSON e manda pro Python
    public void envie(Comunicado x) throws Exception {
        String json = mapeadorJson.writeValueAsString(x);
        
        // Exclusão Mútua: Apenas uma thread envia por vez
        synchronized (mutEx) {
            if (this.sessao != null && this.sessao.isOpen()) {
                sessao.sendMessage(new TextMessage(json));
            } else {
                throw new IllegalStateException("Conexão WebSocket com Backend 2 não está disponível.");
            }
        }
    }

    // A thread espiã fica travada aqui até chegar algo na fila
    public synchronized Comunicado espie() throws Exception {
        while (mensagensRecebidas.isEmpty()) {
            wait();
        }
        return mensagensRecebidas.peek(); // Só olha quem é o primeiro da fila
    }

    public synchronized void receba() throws Exception {
        mensagensRecebidas.take(); // Remove o primeiro da fila
        notifyAll();
    }

    public void adeus() throws Exception {
        if (this.sessao != null && this.sessao.isOpen()) {
            this.sessao.close();
        }
    }

    // O Spring WebSocket vai chamar esse método quando o Python responder
    public void adicionarMensagemDoPython(String jsonTexto) throws Exception {
        // 1. Lê a String JSON como uma "árvore"
        JsonNode noh = mapeadorJson.readTree(jsonTexto);
        
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
                    System.out.println("[WS] Aviso: Evento desconhecido recebido do Python: " + evento);
                    break;
            }

            // 4. Se a conversão deu certo, coloca na fila e notifica a thread espiã
            if (comunicadoConvertido != null) {
                synchronized (this) {
                    mensagensRecebidas.put(comunicadoConvertido);
                    notifyAll();
                }
            }
        } else {
            System.out.println("[WS] Aviso: JSON recebido sem o campo 'evento'. Ignorando...");
        }
    }
}