package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;

import java.util.List;

public interface UtenteService {
    UtenteDto autentica(LoginRequest requestLogin);
    String registra(RegisterRequest utente) throws Exception;
    List<UtenteDto> showAll();
}
