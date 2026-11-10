package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UTENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Utente implements UserDetails {

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RUOLO_ID", nullable = false)
    private Ruolo ruolo;

    @Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
    private LocalDateTime dataCreazione;

    @Column(name = "DATA_ULTIMA_MODIFICA")
    private LocalDateTime dataUltimaModifica;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (ruolo == null) {
            return List.of();
        }

        String nomeRuolo = ruolo.getNome().toUpperCase();
        if (!nomeRuolo.startsWith("ROLE_")) {
            nomeRuolo = "ROLE_" + nomeRuolo;
        }
        return List.of(new SimpleGrantedAuthority(nomeRuolo));
    }

    @Override
    public String getPassword() {
        return this.passwordu;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}