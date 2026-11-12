package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.UsersApi;
import com.ecommerce.ecommerce.api.model.JwtResponse;
import com.ecommerce.ecommerce.api.model.LoginRequest;
import com.ecommerce.ecommerce.api.model.RegisterRequest;
import com.ecommerce.ecommerce.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController implements UsersApi {

    @Autowired
    private UtenteService utenteService ;

    @Override
    public ResponseEntity<JwtResponse> register(RegisterRequest dto) throws Exception {
            JwtResponse response = utenteService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest dto) throws Exception {
           JwtResponse response = utenteService.login(dto);
            return ResponseEntity.ok().body(response);
    }


}
