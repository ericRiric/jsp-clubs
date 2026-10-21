package com.example.clubs.Repositories;

import com.example.clubs.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByNom(String nom);

    boolean existsByNom(String nom);
}