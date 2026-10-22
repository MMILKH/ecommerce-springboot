package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer> {
    Utente findByEmail(String email);
}

