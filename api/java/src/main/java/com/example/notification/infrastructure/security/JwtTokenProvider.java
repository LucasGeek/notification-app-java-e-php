package com.example.notification.infrastructure.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.joining;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.example.notification.domain.entities.User;
import com.example.notification.domain.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final UserRepository userRepository;

    @Value("${spring.security.jwt.secret}")
    private String secretKeyConfig;

    @Value("${spring.security.jwt.expiration}")
    private long validityInMs;

    private SecretKey secretKey;

    public JwtTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyConfig);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Criar o token JWT usando UserAuthentication
    public String createToken(UserAuthentication authentication) {
        String userId = authentication.getUserId();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId);
        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(this.secretKey)
                .compact();
    }

    // Obter a autenticação com base no token JWT
    public UserAuthentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token);

        String userId = claims.getBody().getSubject();

        Optional<User> optionalUser = userRepository.findById(UUID.fromString(userId));
        if (optionalUser.isEmpty()) {
            log.error("Usuário com ID {} não encontrado no banco de dados", userId);
            throw new RuntimeException("Usuário não encontrado");
        }

        User user = optionalUser.get();

        Object authoritiesClaim = claims.getBody().get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        return new UserAuthentication(userId, user, authorities);
    }

    // Validar o token JWT
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            log.info("Data de expiração do token: {}", claims.getBody().getExpiration());
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }
}
