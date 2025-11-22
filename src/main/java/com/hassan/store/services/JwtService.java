package com.hassan.store.services;

import com.hassan.store.config.JwtConfig;
import com.hassan.store.entities.Role;
import com.hassan.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public String generateRefreshToken(User user){
        return generateToken(user, jwtConfig.getRefreshExpiration());
    }

    public String generateAccessToken(User user){
        return generateToken(user, jwtConfig.getExpiration());
    }

    private String generateToken(User user, Integer tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
    }

    public Boolean validateToken(String token){
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());

        }catch(JwtException ex){
            return false;
        }
    }

    public Long getUserId(String token){
        var claims = getClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public Role getUserRole(String token){
        var claims = getClaims(token);
        return Role.valueOf(claims.get("role", String.class));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
