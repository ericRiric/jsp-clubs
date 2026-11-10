package com.example.clubs.Repositories;

import com.example.clubs.Models.RefreshToken;
import com.example.clubs.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    RefreshToken findById(long id);
    Boolean existsByToken(String token);
}
