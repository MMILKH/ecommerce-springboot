package com.ecommerce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
    @NotBlank(message = "Il nome è obbligatorio ")
    private String nome;
    @NotBlank(message = "il cognome è obbligatorio")
    private String cognome;
    @NotBlank(message = "L'email è obbligatoria")
    private String email;
    @NotBlank(message = "il numero è obbligatorio")
    private String numero;
    private String indirizzo;
    private RuoloDto ruolo;
}