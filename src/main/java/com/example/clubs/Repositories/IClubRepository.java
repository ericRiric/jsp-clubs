package com.example.clubs.Repositories;

import com.example.clubs.Models.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClubRepository extends JpaRepository<Club, Integer> {
}