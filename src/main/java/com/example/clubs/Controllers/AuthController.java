package com.example.clubs.Controllers;

import com.example.clubs.Models.*;
import com.example.clubs.Services.RefreshTokenService;
import com.example.clubs.Services.TokenService;
import com.example.clubs.Services.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Ref;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, RefreshTokenService refreshTokenService, UtilisateurService utilisateurService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try{
            // Si l'authentification échoue, on ira directement au bloc catch
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String nom = authentication.getName();
            Utilisateur utilisateur = utilisateurService.getUtilisateurParNom(nom);
            String token = tokenService.generateToken(authentication);

            // Refresh token
            RefreshToken refreshToken = refreshTokenService.creerRefreshToken(utilisateur.getId());

            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                    .httpOnly(true)
                    .path("/auth")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Lax")
                    .secure(true)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new AuthResponse(token, nom, utilisateur.getRole().getNom()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @CookieValue("refreshToken") String token,
            HttpServletResponse response
    ) {
        // La valeur de token, sera le uuid généré pour le jeton d'actualisation, on doit donc récupérer le jeton complet dans la base de données pour le valider.
        RefreshToken refreshToken = refreshTokenService.validerRefreshToken(token);

        String nouveauJwtToken = tokenService.generateToken(
                new UsernamePasswordAuthenticationToken(
                        refreshToken.getUser().getNom(),
                        null
                )
        );

        Utilisateur utilisateur = utilisateurService.getUtilisateurParNom(refreshToken.getUser().getNom());

        RefreshToken nouveauRefreshToken = refreshTokenService.creerRefreshToken(utilisateur.getId());
        String role = utilisateur.getRole().getNom();

        ResponseCookie cookie = ResponseCookie.from("refreshToken", nouveauRefreshToken.getToken())
                .httpOnly(true)
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .secure(true)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Invalider le jeton original
        refreshTokenService.invalidateRefreshToken(refreshToken);

        // Retourner l'authentification comme lors de la connexion
        return ResponseEntity.ok(new AuthResponse(
                nouveauJwtToken,
                utilisateur.getNom(),
                role)
        );

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok().build();
    }
}
