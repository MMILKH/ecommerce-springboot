package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.JwtResponse;
import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest dto) throws Exception {
            utenteService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente registrato con successo!");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest dto) throws Exception {
           JwtResponse response = utenteService.login(dto);
            return ResponseEntity.ok().body(response);
    }


}
