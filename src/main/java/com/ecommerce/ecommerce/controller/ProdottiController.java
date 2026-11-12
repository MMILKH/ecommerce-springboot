package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.ProductsApi;
import com.ecommerce.ecommerce.api.model.ProdottoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.ecommerce.ecommerce.service.ProdottoService;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class ProdottiController implements ProductsApi {
    @Autowired
    private ProdottoService prodottoService;


    @Override
    public ResponseEntity<List<ProdottoDto>> getCatalog(String categoria) {
        List<ProdottoDto> prodotti = prodottoService.findProducts(categoria);
        return ResponseEntity.ok(prodotti);
    }
}

