package com.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdottoDto {

    private Integer id;
    private String nome;
    private String descrizioneBreve;
    private String descrizioneEstesa;
    private String immagine;
    private Integer disponibilita;
    private BigDecimal prezzo;

}
