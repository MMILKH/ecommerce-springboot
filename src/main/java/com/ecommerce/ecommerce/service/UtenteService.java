package com.ecommerce.ecommerce.service;


import com.ecommerce.ecommerce.api.model.UtenteDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface UtenteService {
    List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine,String ruolo) throws Exception;
    UtenteDto updateUser(int id,UtenteDto utenteDto) throws Exception;
    void deleteUser(String id) throws Exception;
}
