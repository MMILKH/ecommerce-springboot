package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.RigaCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RigaCarrelloRepository extends JpaRepository<RigaCarrello, Integer> {

    RigaCarrello findByCarrelloIdAndProdottoId(int id, int prodottoId);
    RigaCarrello findById(int id);
    List<RigaCarrello> findByCarrelloId(int carrelloId);


}

