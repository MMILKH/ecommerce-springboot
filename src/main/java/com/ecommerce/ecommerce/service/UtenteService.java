package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.JwtResponse;
import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.Utente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface UtenteService {
    JwtResponse login(LoginRequest requestLogin) throws Exception;
    void register(RegisterRequest utente) throws Exception;
    List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine);
}
