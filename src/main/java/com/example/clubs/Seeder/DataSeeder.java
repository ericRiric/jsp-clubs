package com.example.clubs.Seeder;

import com.example.clubs.Models.Club;
import com.example.clubs.Models.Role;
import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Repositories.IClubRepository;
import com.example.clubs.Repositories.IRoleRepository;
import com.example.clubs.Repositories.IUtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final IClubRepository clubRepository;
    private final IUtilisateurRepository utilisateurRepository;
    private final IRoleRepository roleRepository;

    public DataSeeder(IClubRepository clubRepository, IUtilisateurRepository utilisateurRepository,  IRoleRepository roleRepository) {
        this.clubRepository = clubRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        seedClubs();
        seedRoles();
        seedUtilisateurs();
    }

    private void seedClubs() {
        if (clubRepository.count() > 0) {
            return;
        }
        clubRepository.saveAll(List.of(
                Club.builder().name("Club d'échecs").description("Parties libres et tournois mensuels.").build(),
                Club.builder().name("Club de robotique").description("Conception et programmation de robots.").build(),
                Club.builder().name("Club de théâtre").description("Ateliers d'improvisation et pièces de fin de session.").build(),
                Club.builder().name("Club de photographie").description("Sorties photo et expositions.").build(),
                Club.builder().name("Club de lecture").description("Une rencontre par mois autour d'un livre.").build(),
                Club.builder().name("Club de goon").description("Une rencontre par heure pour goon, collation et viagra inclut. Tout les participants reçoivent une boisson gratuite.").build()

        ));
    }

    private void seedRoles() {
        if (roleRepository.count() > 0) {
            return;
        }
        roleRepository.saveAll(List.of(
                Role.builder().nom("ROLE_USER").build(),
                Role.builder().nom("ROLE_GOONER").build()
        ));
    }

    private void seedUtilisateurs() {
        if (utilisateurRepository.count() > 0) {
            return;
        }
        utilisateurRepository.saveAll(List.of(
                Utilisateur.builder().nom("admin").password("admin123").role(roleRepository.findRoleByNom("ROLE_GOONER")).build(),
                Utilisateur.builder().nom("alice").password("alice123").role(roleRepository.findRoleByNom("ROLE_USER")).build(),
                Utilisateur.builder().nom("bob").password("bob123").role(roleRepository.findRoleByNom("ROLE_USER")).build()
        ));
    }
}