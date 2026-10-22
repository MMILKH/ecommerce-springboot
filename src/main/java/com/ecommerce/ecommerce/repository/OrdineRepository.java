package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository  extends JpaRepository<Ordine, Integer> {
}
