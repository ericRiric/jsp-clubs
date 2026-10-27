package com.example.clubs.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Utilisateur")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nom;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}