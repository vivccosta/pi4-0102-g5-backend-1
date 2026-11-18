package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Certificado;
import br.com.timbro.backend1.domain.StatusCertificado;
import br.com.timbro.backend1.infra.websocket.GerenciadorDeCorrelacao;
import br.com.timbro.backend1.infra.websocket.Parceiro;
import br.com.timbro.backend1.repository.CertificadoRepository;
import br.com.timbro.backend1.service.HashService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CertificadoController.class)
@AutoConfigureMockMvc(addFilters = false) // Desliga o JWT temporariamente para testarmos apenas a lógica da rota pública
class CertificadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Atributos necessários para o Controlle rodar
    @MockBean private CertificadoRepository certificadoRepository;
    @MockBean private Parceiro parceiroBlockchain;
    @MockBean private GerenciadorDeCorrelacao correlacao;
    @MockBean private HashService hashService;

    @Test
    @DisplayName("Deve retornar certificado válido ao pesquisar por chave existente no banco")
    void deveRetornarValidoQuandoChaveExiste() throws Exception {
        // Arrange: Ensina o "dublê" do MongoDB a responder o que queremos
        Certificado certFalso = new Certificado();
        certFalso.setNomeAluno("Emely");
        certFalso.setCurso("Sistemas de Informação");
        certFalso.setDataConclusao(LocalDate.of(2028, 12, 10));
        certFalso.setHash("0x12345");
        certFalso.setStatus(StatusCertificado.VALIDO);

        // Quando o Controller procurar por "0x12345", o dublê devolve o certFalso
        Mockito.when(certificadoRepository.findByHash("0x12345")).thenReturn(Optional.of(certFalso));

        // Act & Assert: Simula a requisição POST via formulário (multipart)
        mockMvc.perform(post("/api/certificados/validar")
                .param("tipoValidacao", "CHAVE")
                .param("chaveAutenticacao", "0x12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valido").value(true))
                .andExpect(jsonPath("$.nomeAluno").value("Emely"))
                .andExpect(jsonPath("$.curso").value("Sistemas de Informação"));
    }

    @Test
    @DisplayName("Deve retornar invalido ao pesquisar por chave inexistente")
    void deveRetornarInvalidoQuandoChaveNaoExiste() throws Exception {
        // Arrange: Ensina o banco a retornar "Vazio" para uma chave qualquer
        Mockito.when(certificadoRepository.findByHash(Mockito.anyString())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/certificados/validar")
                .param("tipoValidacao", "CHAVE")
                .param("chaveAutenticacao", "0x00000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valido").value(false))
                .andExpect(jsonPath("$.mensagem").value("Certificado inválido: Chave de autenticação desconhecida."));
    }
}