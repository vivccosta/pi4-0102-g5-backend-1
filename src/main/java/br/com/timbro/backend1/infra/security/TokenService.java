package br.com.timbro.backend1.infra.security;

import br.com.timbro.backend1.domain.Emissor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.security.jwt.secret}")
    private String secret;

    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Emissor emissor) {
        return Jwts.builder()
                .subject(emissor.getEmail())
                .claim("perfil", emissor.getPerfil() != null ? emissor.getPerfil() : "EMISSOR") // Prepara para CT1 e CT17
                .claim("id", emissor.getEmissorId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}