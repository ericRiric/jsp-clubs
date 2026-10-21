package com.example.clubs.Controllers;

import com.example.clubs.DTOs.UtilisateurRequestDTO;
import com.example.clubs.DTOs.UtilisateurResponseDTO;
import com.example.clubs.Services.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<UtilisateurResponseDTO> findAll() {
        // Retourne la liste d'objets sérialisés au format JSON
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public UtilisateurResponseDTO findById(@PathVariable int id) {
        return utilisateurService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UtilisateurResponseDTO create(@RequestBody UtilisateurRequestDTO dto) {
        return utilisateurService.create(dto);
    }

    @PutMapping("/{id}")
    public UtilisateurResponseDTO update(@PathVariable int id, @RequestBody UtilisateurRequestDTO dto) {
        return utilisateurService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        utilisateurService.delete(id);
    }
}