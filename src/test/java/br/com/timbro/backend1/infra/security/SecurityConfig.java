package br.com.timbro.backend1.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita a proteção CSRF, pois a API é Stateless e usará JWT
                .csrf(csrf -> csrf.disable())
                
                // Define que não haverá sessão guardada na memória do servidor (cada requisição é independente)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // Configuração das rotas baseadas nos CT da Especificação
                .authorizeHttpRequests(req -> {
                    // CT1: O login precisa ser público para o emissor conseguir pegar o token
                    req.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    
                    // CT9: A validação pública do certificado pode ser acessada por qualquer pessoa
                    req.requestMatchers(HttpMethod.POST, "/api/certificados/validar").permitAll();
                    
                    // Qualquer outra rota (registro, histórico, revogação, ativação) exigirá autenticação
                    req.anyRequest().authenticated();
                })
                .build();
    }

    // Configura o BCrypt como o algoritmo padrão de hash de senhas da aplicação
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}