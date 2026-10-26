package com.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RigaCarrelloDto {

    private ProdottoDto prodotto;
    private Integer quantita;
    private CarrelloDto carrello;

}
