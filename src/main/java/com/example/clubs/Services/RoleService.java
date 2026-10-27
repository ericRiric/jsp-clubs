package com.example.clubs.Services;

import com.example.clubs.Models.Role;
import com.example.clubs.Repositories.IRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String nom){return roleRepository.findRoleByNom(nom);}
}