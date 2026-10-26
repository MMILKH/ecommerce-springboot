package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "UTENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "COGNOME", nullable = false, length = 50)
    private String cognome;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "NUMERO", length = 20)
    private String numero;

    @Column(name = "INDIRIZZO", length = 255)
    private String indirizzo;

    @Column(name = "PASSWORDU", nullable = false, length = 255)
    private String passwordu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RUOLO_ID", nullable = false)
    private Ruolo ruolo;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private Date dataCreazione;

    @Column(name = "DATA_ULTIMA_MODIFICA")
    private Date dataUltimaModifica;

}