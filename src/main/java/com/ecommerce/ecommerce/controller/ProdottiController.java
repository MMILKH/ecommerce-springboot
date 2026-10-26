package com.ecommerce.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import com.ecommerce.ecommerce.dto.ProdottoDto;
import com.ecommerce.ecommerce.service.ProdottoService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProdottiController {
    @Autowired
    private ProdottoService prodottoService;


    @GetMapping("/catalog")
    public ResponseEntity<List<ProdottoDto>> getCatalogo(@RequestParam(required = false) String categoria) {
        List<ProdottoDto> prodotti = prodottoService.findProducts(categoria);
        return ResponseEntity.ok(prodotti);
    }
}

