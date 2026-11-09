package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.OrdineDto;
import com.ecommerce.ecommerce.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrdineDto> checkout(@RequestBody OrdineDto ordine,@PathVariable int userId) {
        OrdineDto response = ordineService.createOrder(ordine,userId);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
