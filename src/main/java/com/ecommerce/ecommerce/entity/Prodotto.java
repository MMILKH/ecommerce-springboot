package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODOTTO")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "SKU", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "DESCRIZIONEB", length = 255)
    private String descrizioneb;

    @Column(name = "DESCRIZIONEE", columnDefinition = "TEXT")
    private String descrizionee;

    @Column(name = "IMMAGINE", length = 255)
    private String immagine;

    @Column(name = "CATEGORIA", length = 50)
    private String categoria;

    @Column(name = "DISPONIBILITA", nullable = false)
    private int disponibilita;

    @Column(name = "STATO_PUB", nullable = false)
    private int statoPub;

    @Column(name = "PREZZO", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzo;
}