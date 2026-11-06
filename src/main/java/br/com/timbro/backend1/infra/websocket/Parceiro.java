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

    // O Spring WebSocket chama esse método quando o Python responde
    public void adicionarMensagemDoPython(String jsonTexto) throws Exception {
        if (jsonTexto == null) {
            throw new Exception("O Json não foi recebido");
        }
        
        JsonNode rootNode = mapeadorJson.readTree(jsonTexto);

        int stringCount = 0;
        int intCount = 0;
        int totalFields = rootNode.size();

        Iterator> fields = rootNode.fields();
        while (fields.hasNext()) {
            JsonNode valorNode = fields.next().getValue();
                
            if (valorNode.isTextual()) {
                stringCount++;
            } else if (valorNode.isInt()) {
                intCount++;
            }
        }

        Comunicado comunicadoConvertido = null;

        if (totalFields == 1 && stringCount == 1) {
            comunicadoConvertido = mapeadorJson.readValue(jsonTexto, ConsultarHashWS.class);
        } else if (totalFields == 4 && stringCount == 3 && intCount == 1) {
            comunicadoConvertido = mapeadorJson.readValue(jsonTexto, GravarCertificadoWS.class);
        } else {
            throw new Exception("O JSON não corresponde a nenhum dos formatos esperados (nem Consulta, nem Gravação).");
        }

        // Adicionando na fila de mensagens recebidas
        if (comunicadoConvertido != null) {
            mensagensRecebidas.put(comunicadoConvertido);
        }
    }
}