package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Certificado;
import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.domain.StatusCertificado;
import br.com.timbro.backend1.repository.CertificadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmissorController.class)
@AutoConfigureMockMvc(addFilters = false) // Desligamos os filtros reais para injetar o nosso usuário manualmente
class EmissorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificadoRepository certificadoRepository;

    @MockBean
    private br.com.timbro.backend1.repository.EmissorRepository emissorRepository; 

    private Emissor emissorLogadoFalso;

    @BeforeEach
    void prepararSeguranca() {
        // Cria um usuário falso com ID 102
        emissorLogadoFalso = new Emissor();
        emissorLogadoFalso.setEmissorId(102);
        emissorLogadoFalso.setPerfil("EMISSOR");

        // Engana o Spring Security dizendo que o ID 102 já fez login
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(emissorLogadoFalso, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("Deve listar certificados (HTTP 200) quando o emissor acessa o próprio ID")
    void deveListarCertificadosDoProprioEmissor() throws Exception {
        // Arrange
        Certificado cert = new Certificado();
        cert.setCertificadoId(1);
        cert.setNomeAluno("João");
        cert.setHash("0xABC");
        cert.setStatus(StatusCertificado.VALIDO);
        cert.setDataEmissao(LocalDateTime.now());

        Mockito.when(certificadoRepository.findByEmissorId(102)).thenReturn(List.of(cert));

        // Act & Assert
        mockMvc.perform(get("/api/emissores/102/certificados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeAluno").value("João"))
                .andExpect(jsonPath("$[0].hash").value("0xABC"));
    }

    @Test
    @DisplayName("Deve bloquear acesso (HTTP 403) quando emissor tenta ver histórico de outro ID")
    void deveBloquearAcessoAOutroEmissor() throws Exception {
        // Act & Assert: Tenta acessar o ID 105 usando o token do ID 102
        mockMvc.perform(get("/api/emissores/105/certificados"))
                .andExpect(status().isForbidden());
    }
}