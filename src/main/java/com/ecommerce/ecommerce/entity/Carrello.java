package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTENTE_ID", nullable = false)
    private Utente utente;

    @Column(name = "STATO", nullable = false, length = 20)
    private String stato;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private Date dataCreazione;

    @Column(name = "ULTIMO_AGGIORNAMENTO")
    private Date ultimoAggiornamento;

}