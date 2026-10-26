package com.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrelloDto {

    private Integer id;
    private String stato;
    private Date dataCreazione;
    private Date ultimoAggiornamento;

    private LoginRequest utente;

}
