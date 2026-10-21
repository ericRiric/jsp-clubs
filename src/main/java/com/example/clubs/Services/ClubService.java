package com.example.clubs.Services;

import com.example.clubs.Models.Club;
import com.example.clubs.Repositories.IClubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    private final IClubRepository clubRepository;

    public ClubService(IClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    public Club findById(int id) {
        return getOrThrow(id);
    }

    public Club create(Club club) {
        Club newClub = Club.builder()
                .name(club.getName())
                .description(club.getDescription())
                .build();
        return clubRepository.save(newClub);
    }

    public Club update(int id, Club club) {
        Club existing = getOrThrow(id);
        existing.setName(club.getName());
        existing.setDescription(club.getDescription());
        return clubRepository.save(existing);
    }

    public void delete(int id) {
        if (!clubRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found");
        }
        clubRepository.deleteById(id);
    }

    private Club getOrThrow(int id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
    }
}