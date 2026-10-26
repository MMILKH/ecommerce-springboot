package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.Utente;
import com.ecommerce.ecommerce.service.impl.UtenteServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UtenteController {

    @Autowired
    private UtenteServiceImpl utenteServiceImpl;


    @GetMapping("/admin")
    public ResponseEntity<List<UtenteDto>> searchUtentiAdmin(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInizio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFine) {

        List<UtenteDto> utenti = utenteServiceImpl.getUsers(nome, cognome, email, numero, dataInizio, dataFine);
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
