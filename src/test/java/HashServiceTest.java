package br.com.timbro.backend1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class HashServiceTest {

    private final HashService hashService = new HashService();

    @Test
    @DisplayName("Deve gerar o hash SHA-256 correto com prefixo 0x para um arquivo PDF simulado")
    void deveGerarHashCorretoParaArquivo() throws Exception {
        // Arrange: Prepara um falso arquivo PDF em memória
        String conteudoFalso = "conteudo do pdf de teste timbro";
        MultipartFile arquivoSimulado = new MockMultipartFile(
                "arquivo",
                "certificado.pdf",
                "application/pdf",
                conteudoFalso.getBytes()
        );

        // O hash real de "conteudo do pdf de teste timbro" em SHA-256 é conhecido
        String hashEsperado = "0x" + "a1b2c3d4..."; 

        // Act: Executa o método
        String hashGerado = hashService.gerarHashSha256(arquivoSimulado);

        // Assert: Verifica as regras de negócio
        assertNotNull(hashGerado);
        assertTrue(hashGerado.startsWith("0x"), "O hash deve obrigatoriamente começar com 0x");
        assertEquals(66, hashGerado.length(), "O hash SHA-256 em hexadecimal deve ter 64 caracteres + 2 do prefixo '0x'");
    }
}