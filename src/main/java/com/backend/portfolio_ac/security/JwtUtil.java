package com.backend.portfolio_ac.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilidad para la generación y validación de JWTs.
 * Proporciona métodos para crear tokens, extraer información del token y validar su integridad.
 *
 * @author bunnystring
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT usando el nombre de usuario dado.
     *
     * @param username El nombre de usuario (suele ser el email)
     * @return Token JWT generado
     */
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extrae el email (subject) de un token JWT.
     *
     * @param token El token JWT
     * @return El email extraído del token
     */
    public String getEmailFromToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token) // CORREGIDO: Usar parseClaimsJws para tokens firmados
                .getBody()
                .getSubject();
    }

    /**
     * Valida la integridad y validez de un token JWT.
     *
     * @param token El token JWT
     * @return true si es válido, false en caso contrario
     */
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); // CORREGIDO
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}