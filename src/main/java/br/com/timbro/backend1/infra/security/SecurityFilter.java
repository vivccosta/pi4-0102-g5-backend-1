package br.com.timbro.backend1.infra.security;

import br.com.timbro.backend1.domain.Emissor;
import br.com.timbro.backend1.repository.EmissorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmissorRepository emissorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = recuperarToken(request);

        if (token != null) {
            try {
                String email = tokenService.extrairEmail(token);
                Optional<Emissor> emissorOpt = emissorRepository.findByEmail(email);

                if (emissorOpt.isPresent()) {
                    Emissor emissor = emissorOpt.get();
                    
                    // Cria a autoridade baseada no perfil do banco de dados (ex: ROLE_ADMINISTRADOR ou ROLE_EMISSOR)
                    String role = emissor.getPerfil() != null ? "ROLE_" + emissor.getPerfil() : "ROLE_EMISSOR";
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                    var authentication = new UsernamePasswordAuthenticationToken(emissor, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Se o token for inválido, expirado ou fraudado, o fluxo segue sem autenticar (gerando erro 401 nas rotas protegidas)
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}