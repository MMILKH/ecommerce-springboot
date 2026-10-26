package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.Utente;
import com.ecommerce.ecommerce.service.impl.UtenteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private UtenteServiceImpl utenteServiceImpl;



    @GetMapping
    public ResponseEntity<List<UtenteDto>> getTuttiGliUtenti() {
        List<UtenteDto> utenti = utenteServiceImpl.showAll();
        return ResponseEntity.ok(utenti);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String esito = utenteServiceImpl.registra(dto);
        if ("ok".equals(esito)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo!");
        } else {
            return ResponseEntity.badRequest().body(esito);
        }
    }

}
