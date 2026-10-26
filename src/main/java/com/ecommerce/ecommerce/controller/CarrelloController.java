package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import com.ecommerce.ecommerce.service.CarrelloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;
    @PostMapping("add")
    public ResponseEntity<String> addToCart(@RequestBody RigaCarrelloDto dto) {

        String response = carrelloService.addFromCatalog(dto);
        if (response.equals("ok")) {
            return ResponseEntity.ok(dto.toString());
        }
        return ResponseEntity.badRequest().body("non è stato possibile aggiungere la riga al carrello");



    }
}
