package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RIGA_CARRELLO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RigaCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARRELLO_ID", nullable = false)
    private Carrello carrello;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODOTTO_ID", nullable = false)
    private Prodotto prodotto;

    @Column(name = "QUANTITA", nullable = false)
    private int quantita;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @Column(name = "DATA_ULTIMA_MODIFICA")
    private LocalDateTime dataUltimaModifica;



}