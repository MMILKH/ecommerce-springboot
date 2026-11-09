package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.service.impl.UtenteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UtenteController {

    @Autowired
    private UtenteServiceImpl utenteServiceImpl;


    @GetMapping("/admin")
    public ResponseEntity<List<UtenteDto>> searchUserAdmin(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) @Valid@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(required = false) @Valid@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine){

        List<UtenteDto> utenti = utenteServiceImpl.getUsers(nome, cognome, email, numero, dataInizio, dataFine);
        return ResponseEntity.ok(utenti);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            utenteServiceImpl.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo!");
    }

    @PostMapping("/login")
    public ResponseEntity<UtenteDto> login(@Valid @RequestBody LoginRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
           UtenteDto  response = utenteServiceImpl.login(dto);
            return ResponseEntity.ok().body(response);
    }


}
