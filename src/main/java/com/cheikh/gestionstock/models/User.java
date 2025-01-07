package com.cheikh.gestionstock.models;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String password;
    @Column(updatable = false, nullable = false)
    @BooleanFlag
    private boolean admin;
    private String sessionKey = null;
}
