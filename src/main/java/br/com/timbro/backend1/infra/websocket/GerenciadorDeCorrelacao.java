package br.com.timbro.backend1.infra.websocket;

import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GerenciadorDeCorrelacao {

    // Guarda a thread que está esperando uma resposta, usando um ID único (seja o uuid ou o próprio Hash)
    private final ConcurrentHashMap<String, CompletableFuture<Comunicado>> chamadasPendentes = new ConcurrentHashMap<>();

    // O Controller HTTP chama isso e fica travado esperando
    public CompletableFuture<Comunicado> registrarEspera(String idCorrelacao) {
        CompletableFuture<Comunicado> futuro = new CompletableFuture<>();
        chamadasPendentes.put(idCorrelacao, futuro);
        return futuro;
    }

    // A Thread Espiã chama o retorno de CompletableFuture quando o Python devolve a resposta
    public void liberarEspera(String idCorrelacao, Comunicado respostaDoPython) {
        CompletableFuture<Comunicado> futuro = chamadasPendentes.remove(idCorrelacao);
        if (futuro != null) {
            futuro.complete(respostaDoPython); // Acorda a thread do HTTP e devolve o dado
        }
    }
}