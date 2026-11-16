package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.api.model.JwtResponse;
import com.ecommerce.ecommerce.api.model.LoginRequest;
import com.ecommerce.ecommerce.api.model.RegisterRequest;
import com.ecommerce.ecommerce.api.model.UtenteDto;
import com.ecommerce.ecommerce.entity.Ruolo;
import com.ecommerce.ecommerce.entity.Utente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface UtenteService {
    JwtResponse login(LoginRequest requestLogin) throws Exception;
    JwtResponse register(RegisterRequest utente) throws Exception;
    List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine,String ruolo) throws Exception;
    UtenteDto updateUser(int id,UtenteDto utenteDto) throws Exception;
    void deleteUser(int id) throws Exception;
}
