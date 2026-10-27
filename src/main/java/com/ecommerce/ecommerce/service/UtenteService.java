package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface UtenteService {
    String login(LoginRequest requestLogin);
    String register(RegisterRequest utente) throws Exception;
    List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine);
}
