package com.ecommerce.ecommerce.dto;

import com.ecommerce.ecommerce.entity.Carrello;
import com.ecommerce.ecommerce.entity.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RigaOrdineDto {
    private BigDecimal prezzoRiga;
    private BigDecimal prezzoUnitario;
    private LocalDateTime dataCreazione;
    private Integer rigaCarrelloId;
    private Integer quantita;
    private ProdottoDto prodotto;
    private CarrelloDto carrello;
}
