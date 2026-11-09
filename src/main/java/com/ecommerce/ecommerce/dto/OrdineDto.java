package com.ecommerce.ecommerce.dto;

import com.ecommerce.ecommerce.entity.Stato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdineDto {
    private LocalDateTime dataCreazione;
    private String indirizzoSpedizione;
    private String indirizzoFatturazione;
    private BigDecimal importo;
    private String codice;
    private Stato stato;
}
