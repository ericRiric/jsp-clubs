package com.example.clubs.Services;

import com.example.clubs.Models.RefreshToken;
import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Repositories.IRefreshTokenRepository;
import com.example.clubs.Repositories.IUtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final IRefreshTokenRepository refreshTokenRepository;
    private final IUtilisateurRepository utilisateurRepository;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository, IUtilisateurRepository utilisateurRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public RefreshToken creerRefreshToken(Integer userId) {
        Utilisateur user = utilisateurRepository.findById(userId).get();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validerRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken == null) {
            return null;
        } else if (Instant.now().isBefore(refreshToken.getExpiryDate()) && refreshToken.getIsValid()) {
            return refreshToken;
        } else {
            return null;
        }
    }

    @Transactional
    public void invalidateRefreshToken(RefreshToken token) {
        RefreshToken refreshToken = refreshTokenRepository.findById(token.getId()).orElse(null);
        if (refreshToken != null) {
            refreshToken.setIsValid(false);
        }
    }
}
