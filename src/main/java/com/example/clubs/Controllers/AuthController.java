package com.example.clubs.Controllers;

import com.example.clubs.Models.AuthResponse;
import com.example.clubs.Models.LoginRequest;
import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Services.TokenService;
import com.example.clubs.Services.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, UtilisateurService utilisateurService , AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try{
            // Si l'authentification échoue, on ira directement au bloc catch
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String nom = authentication.getName();
            Utilisateur utilisateur = utilisateurService.getUtilisateurParNom(nom);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token, nom, utilisateur.getRole().getNom()));

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok().build();
    }
}
