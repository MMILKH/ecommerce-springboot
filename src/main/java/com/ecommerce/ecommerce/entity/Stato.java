package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STATO")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Stato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "DESCRIZIONE", length = 255)
    private String descrizione;
}