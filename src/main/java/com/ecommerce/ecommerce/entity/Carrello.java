package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "CARRELLO")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "UTENTE_ID", nullable = false, length = 40)
    private String utente;

    @Column(name = "STATO", nullable = false, length = 20)
    private String stato;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @Column(name = "ULTIMO_AGGIORNAMENTO")
    private LocalDateTime ultimoAggiornamento;

}