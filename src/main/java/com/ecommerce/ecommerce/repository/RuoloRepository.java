package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
    Ruolo findByNome(String nome);

}