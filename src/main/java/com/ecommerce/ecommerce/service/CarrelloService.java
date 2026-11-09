package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public interface CarrelloService {


    CarrelloDto getActiveCart(int utenteId);

    RigaCarrelloDto addFromCatalog(RigaCarrelloDto rigaCarrelloDto,int cartId);

//    BigDecimal getTotal(int carrelloId);

    List<RigaCarrelloDto> getRowByCartId(int carrelloId);

    RigaCarrelloDto getRowByCartIdAndProductsId(int cartId,int prodottoId);

    RigaCarrelloDto updateRowQuantity(int rigaId, int nuovaQuantita);

    void deleteRow(int rigaId);

//    int countElements(int utenteId);

    boolean existRiga(int rigaId);


}
