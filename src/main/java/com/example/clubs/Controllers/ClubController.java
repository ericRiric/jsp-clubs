package com.example.clubs.Controllers;

import com.example.clubs.Models.Club;
import com.example.clubs.Services.ClubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public List<Club> findAll() {
        return clubService.findAll();
    }

    @GetMapping("/{id}")
    public Club findById(@PathVariable int id) {
        return clubService.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Club create(@RequestBody Club club) {
        return clubService.create(club);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public Club update(@PathVariable int id, @RequestBody Club club) {
        return clubService.update(id, club);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        clubService.delete(id);
    }
}
