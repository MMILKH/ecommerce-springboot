package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
    Carrello findByUtenteIdAndStato(int utenteId, String stato);

    Carrello findById(int id);
    boolean existsById(int id);

    Carrello findByIdAndStato(Integer id, String aNew);
}
