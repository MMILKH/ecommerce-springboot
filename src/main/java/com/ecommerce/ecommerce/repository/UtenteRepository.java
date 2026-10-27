package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer>, JpaSpecificationExecutor<Utente> {
    Utente findByEmail(String email);
}

