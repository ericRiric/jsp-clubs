package com.example.clubs.Seeder;

import com.example.clubs.Models.Club;
import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Repositories.IClubRepository;
import com.example.clubs.Repositories.IUtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final IClubRepository clubRepository;
    private final IUtilisateurRepository utilisateurRepository;

    public DataSeeder(IClubRepository clubRepository, IUtilisateurRepository utilisateurRepository) {
        this.clubRepository = clubRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void run(String... args) {
        seedClubs();
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

    private void seedUtilisateurs() {
        if (utilisateurRepository.count() > 0) {
            return;
        }
        utilisateurRepository.saveAll(List.of(
                Utilisateur.builder().nom("admin").password("admin123").build(),
                Utilisateur.builder().nom("alice").password("alice123").build(),
                Utilisateur.builder().nom("bob").password("bob123").build()
        ));
    }
}