package br.com.timbro.backend1.infra.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
public class BlockchainWebSocketConfig {

    @Value("${BLOCKCHAIN_WS_URL:ws://backend2:8000/ws/blockchain}")
    private String blockchainWsUrl;

    @Autowired
    private Parceiro parceiro;

    @Autowired(required = false)
    private TratadoraDeMensagensWS tratadora;

    // Inicia a conexão websocket
    @EventListener(ApplicationReadyEvent.class)
    public void conectarAoBackend2() {
        try {
            StandardWebSocketClient client = new StandardWebSocketClient();

            TextWebSocketHandler handler = new TextWebSocketHandler() {
                @Override
                public void afterConnectionEstablished(WebSocketSession session) {
                    parceiro.setSessao(session);
                    System.out.println("[WS] Conectado com sucesso ao Backend 2 em: " + blockchainWsUrl);

                    if (tratadora != null && !tratadora.isAlive()) {
                        tratadora.start();
                    }
                }

                @Override
                protected void handleTextMessage(WebSocketSession session, TextMessage message) {
                    String payload = message.getPayload();
                    System.out.println("[WS] Mensagem recebida do Backend 2: " + payload);
                }
            };

            client.execute(handler, blockchainWsUrl).get();
        } catch (Exception e) {
            System.err.println("[WS] Erro ao conectar no Backend 2 (" + blockchainWsUrl + "): " + e.getMessage());
        }
    }
}