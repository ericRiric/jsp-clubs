package com.example.clubs.Repositories;

import com.example.clubs.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByNom(String nom);
}