package com.example.clubs.Security;


import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Repositories.IUtilisateurRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUtilisateurRepository userRepository;
    public CustomUserDetailsService(IUtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findDistinctFirstByNom(username);

        if (utilisateur != null) {
            // Cet utilisateur est celui qui sera conservé dans l'authentification
            return new User(
                    username,
                    utilisateur.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(utilisateur.getRole().getNom()))
            );
        } else {
            throw new UsernameNotFoundException("Nom d'utilisateur ou mot de passe invalide");
        }
    }
}