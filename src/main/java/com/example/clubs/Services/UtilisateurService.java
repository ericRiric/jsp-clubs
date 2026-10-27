package com.example.clubs.Services;

import com.example.clubs.DTOs.UtilisateurRequestDTO;
import com.example.clubs.DTOs.UtilisateurResponseDTO;
import com.example.clubs.Models.Utilisateur;
import com.example.clubs.Repositories.IUtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {
    private final IUtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(IUtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UtilisateurResponseDTO> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UtilisateurResponseDTO findById(int id) {
        return toResponse(getOrThrow(id));
    }

    public UtilisateurResponseDTO create(UtilisateurRequestDTO dto) {
        if (utilisateurRepository.existsByNom(dto.getNom())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(dto.getNom())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        return toResponse(utilisateurRepository.save(utilisateur));
    }

    public UtilisateurResponseDTO update(int id, UtilisateurRequestDTO dto) {
        Utilisateur existing = getOrThrow(id);
        if (!existing.getNom().equals(dto.getNom()) && utilisateurRepository.existsByNom(dto.getNom())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        existing.setNom(dto.getNom());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return toResponse(utilisateurRepository.save(existing));
    }

    public void delete(int id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found");
        }
        utilisateurRepository.deleteById(id);
    }

    private Utilisateur getOrThrow(int id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
    }

    private UtilisateurResponseDTO toResponse(Utilisateur utilisateur) {
        return new UtilisateurResponseDTO(utilisateur.getId(), utilisateur.getNom());
    }
}