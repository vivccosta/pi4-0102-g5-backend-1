package br.com.timbro.backend1.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErros {

    // 1. Captura erros de validação (HTTP 400 - Campos obrigatórios ausentes)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
        String camposComErro = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Campos obrigatórios inválidos ou ausentes: " + camposComErro
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 2. Captura erros de negócio lançados manualmente no código (HTTP 401, 404, etc)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResposta> tratarErroDeNegocio(ResponseStatusException ex) {
        ErroResposta erro = new ErroResposta(
                ex.getStatusCode().value(),
                ex.getReason() != null ? ex.getReason() : "Erro processando a requisição"
        );
        return ResponseEntity.status(ex.getStatusCode()).body(erro);
    }

    // 3. Captura qualquer outro erro inesperado (HTTP 500 - Falha interna)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGenerico(Exception ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Falha interna no servidor: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}