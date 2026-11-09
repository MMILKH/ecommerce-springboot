package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import com.ecommerce.ecommerce.entity.Carrello;
import com.ecommerce.ecommerce.handler.GlobalExceptionHandler;
import com.ecommerce.ecommerce.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cart")

public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;
    @PostMapping("/{cartId}/add")
    public ResponseEntity<RigaCarrelloDto> addToCart(@RequestBody RigaCarrelloDto dto, @PathVariable int cartId) {
            dto = carrelloService.addFromCatalog(dto, cartId);
            return ResponseEntity.ok(dto);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CarrelloDto> getCart(@PathVariable Integer userId) {
        CarrelloDto c = carrelloService.getActiveCart(userId);
            List<RigaCarrelloDto> righe = carrelloService.getRowByCartId(c.getId());
            c.setRighe(righe);
            return ResponseEntity.ok(c);
    }

    @PatchMapping("/{rowId}")
    public ResponseEntity<RigaCarrelloDto> updateCartRow(@RequestBody RigaCarrelloDto dto,@PathVariable int rowId) {

                RigaCarrelloDto nuovaRiga = carrelloService.updateRowQuantity(rowId, dto.getQuantita());
                return ResponseEntity.ok(nuovaRiga);

    }

    @DeleteMapping("/{rowId}")
    public ResponseEntity<Void> deleteRow(@PathVariable int rowId) {
        carrelloService.deleteRow(rowId);
        return ResponseEntity.noContent().build();
    }

}
