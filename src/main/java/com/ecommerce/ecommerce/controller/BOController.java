package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.AdminApi;
import com.ecommerce.ecommerce.api.model.ProdottoDto;
import com.ecommerce.ecommerce.api.model.UtenteDto;
import com.ecommerce.ecommerce.service.ProdottoService;
import com.ecommerce.ecommerce.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class BOController implements AdminApi {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ProdottoService prodottoService;


    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UtenteDto>> searchUserAdmin(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String ruolo,
            @RequestParam(required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
            @RequestParam(required = false) @Valid@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine) throws Exception {

        List<UtenteDto> utenti = utenteService.getUsers(nome, cognome, email, numero, dataInizio, dataFine, ruolo);
        return ResponseEntity.ok(utenti);
    }

}