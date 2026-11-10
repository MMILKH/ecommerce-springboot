package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/admin")
public class BOController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    public ResponseEntity<List<UtenteDto>> searchUserAdmin(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(required = false) @Valid@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine) {

        List<UtenteDto> utenti = utenteService.getUsers(nome, cognome, email, numero, dataInizio, dataFine);
        return ResponseEntity.ok(utenti);
    }
}