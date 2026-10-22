package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.RigaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RigaOrdineRepository extends JpaRepository <RigaOrdine,Integer>{

    RigaOrdine findByOrdineIdAndProdottoId(int ordineId, int prodottoId);
    RigaOrdine findById(int id);
    List<RigaOrdine> findByOrdineId(int ordineId);
}
