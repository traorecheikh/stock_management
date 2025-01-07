package com.cheikh.gestionstock.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String categorie;
    private Integer quantite;
    private Double prix;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
