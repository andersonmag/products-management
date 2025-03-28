package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.config.JwtPropertiesConfig;
import com.example.empiretechtestbackendjava.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtPropertiesConfig jwtProperties;
    private final UserRepository userRepository;

    public String generateToken(String username) {

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()), Jwts.SIG.HS256)
                .compact();
        return jwtProperties.getTokenPrefix() + " " + token;
    }

    public Authentication validAuthenticationToken(String token) throws JwtException {

        try {
            var username = extractUsernameFromToken(token);

            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes())).build()
                    .parseSignedClaims(token.replace(jwtProperties.getTokenPrefix() + " ", ""))
                    .getPayload()
                    .getSubject();

            if (username != null) {
                var user = userRepository.findByUsername(username).orElse(null);

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>());
                }
            }
        } catch (JwtException exception) {
            throw exception;
        }
        return null;
    }

    private String extractUsernameFromToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes())).build()
                .parseSignedClaims(token.replace(jwtProperties.getTokenPrefix() + " ", ""))
                .getPayload()
                .getSubject();
    }

    public String getHeaderToken() {
        return jwtProperties.getTokenHeader();
    }
}
