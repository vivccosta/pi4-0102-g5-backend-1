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

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita a proteção CSRF, pois a API é Stateless e usará JWT
                .csrf(csrf -> csrf.disable())
                
                // Define que não haverá sessão guardada na memória do servidor (cada requisição é independente)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .cors(cors -> cors.configure(http))
                
                // Configuração das rotas baseadas nos CT da Especificação
                .authorizeHttpRequests(req -> {
                    // CT1: O login precisa ser público para o emissor conseguir pegar o token
                    req.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                    
                    // CT9: A validação pública do certificado pode ser acessada por qualquer pessoa
                    req.requestMatchers(HttpMethod.POST, "/api/certificados/validar").permitAll();

                    // Permite que empresas possam se cadastrar
                    req.requestMatchers(HttpMethod.POST, "/api/emissores/cadastrar").permitAll();
                    
                    // Apenas administradores podem acessar rotas que começam com /api/admin
                    req.requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR");

                    // Qualquer outra rota (registro, histórico, revogação, ativação) exigirá autenticação
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configura o BCrypt como o algoritmo padrão de hash de senhas da aplicação
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        // Permite o endereço do frontend da Viviane (em produção, você coloca a URL real)
        configuration.setAllowedOrigins(java.util.List.of("http://localhost:3000", "http://localhost:5173")); 
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.List.of("*"));
        configuration.setAllowCredentials(true);
        
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}