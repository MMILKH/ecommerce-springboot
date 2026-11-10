package com.ecommerce.ecommerce.dto;

public class JwtResponse {
    private String token;
    private UtenteDto utente;

    public JwtResponse() {}

    public JwtResponse(String token, UtenteDto utente) {
        this.token = token;
        this.utente = utente;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UtenteDto getUtente() {
        return utente;
    }

    public void setUtente(UtenteDto utente) {
        this.utente = utente;
    }
}
