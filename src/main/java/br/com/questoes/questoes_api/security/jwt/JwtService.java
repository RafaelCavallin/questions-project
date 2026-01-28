package br.com.questoes.questoes_api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, java.util.Collection<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Object rolesObj = getClaims(token).get("roles");
        if (rolesObj instanceof List<?>) {
            List<?> rawList = (List<?>) rolesObj;
            List<String> roles = new ArrayList<>();
            for (Object o : rawList) {
                roles.add(String.valueOf(o));
            }
            return roles;
        }
        return java.util.Collections.emptyList();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
