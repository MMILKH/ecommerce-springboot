package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CARRELLO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTENTE_ID", nullable = false)
    private Utente utente;

    @Column(name = "STATO", nullable = false, length = 20)
    private String stato;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @Column(name = "ULTIMO_AGGIORNAMENTO")
    private LocalDateTime ultimoAggiornamento;

}