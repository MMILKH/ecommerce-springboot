package com.ecommerce.ecommerce.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "L'email è obbligatoria")
    private String email;
    @NotBlank(message = "la password è obbligatoria")
    private String password;
}

