package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDINE")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CODICE", nullable = false, unique = true, length = 50)
    private String codice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UTENTE_ID", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATO_ID", nullable = false)
    private Stato stato;

    @Column(name = "IMPORTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal importo;

    @Column(name = "INDIRIZZO_SPEDIZIONE", nullable = false, length = 255)
    private String indirizzoSpedizione;

    @Column(name = "INDIRIZZO_FATTURAZIONE", nullable = false, length = 255)
    private String indirizzoFatturazione;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

}