package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public interface CarrelloService {


    CarrelloDto getActiveCart(int utenteId);

    String addFromCatalog(RigaCarrelloDto rigaCarrelloDto);

    BigDecimal getTotale(int carrelloId);

    List<RigaCarrelloDto> getRigheByCarrelloId(int carrelloId);

    void aggiornaQuantitaRiga(int rigaId, int nuovaQuantita);

    void rimuoviRiga(int rigaId);

    int countElementi(int utenteId);


}