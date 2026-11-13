package br.com.timbro.backend1.controller;

import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.domain.StatusEmissor;
import br.com.timbro.backend1.repository.EmissorRepository;
import br.com.timbro.backend1.infra.security.SecurityConfig;
import br.com.timbro.backend1.infra.security.SecurityFilter;
import br.com.timbro.backend1.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class}) // Importa a configuração real de segurança para testar as portas
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private EmissorRepository emissorRepository;
    @MockBean private TokenService tokenService;
    @MockBean private SecurityFilter securityFilter;

    @Test
    @WithMockUser(roles = "ADMINISTRADOR") // Simula um Token de Admin
    @DisplayName("Administrador deve conseguir acessar a lista de emissores pendentes (HTTP 200)")
    void adminPodeAcessarPendentes() throws Exception {
        Emissor emissorFalso = new Emissor();
        emissorFalso.setEmissorId(10);
        emissorFalso.setNome("Faculdade Teste");
        emissorFalso.setCnpjCpf("11111111111");
        emissorFalso.setStatus(StatusEmissor.PENDENTE);

        Mockito.when(emissorRepository.findByStatus(StatusEmissor.PENDENTE)).thenReturn(List.of(emissorFalso));

        mockMvc.perform(get("/api/admin/emissores/pendentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Faculdade Teste"));
    }

    @Test
    @WithMockUser(roles = "EMISSOR") // Simula um Token de Faculdade Comum
    @DisplayName("Emissor comum deve ser bloqueado ao tentar acessar rota de Admin (HTTP 403)")
    void emissorNaoPodeAcessarPendentes() throws Exception {
        
        mockMvc.perform(get("/api/admin/emissores/pendentes"))
                .andExpect(status().isForbidden()); // O Spring barra antes mesmo de chegar no Controller
    }
}