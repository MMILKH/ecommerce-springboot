package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {

    List<Prodotto> findAllByCategoria(String categoria);
    Prodotto findById(int id);
}

