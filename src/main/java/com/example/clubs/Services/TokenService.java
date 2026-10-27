package com.example.clubs.Services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    // Utiliser l'encodeur créé dans SecurityConfig.java
    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    // Donner en paramètre l'objet authentication pour avoir accès aux détails de l'utilisateur
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        // Récupérer les rôles
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Construire le jeton
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                // On ajoute un "claim" personnalisé contenant les rôles. On pourra s'en servir pour les autorisations.
                // La clé doit corresponde à la clé du convertisseur d'authentification.
                .claim("roles", roles)
                .build();
        // Retourner le jeton créé sous forme de String
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
