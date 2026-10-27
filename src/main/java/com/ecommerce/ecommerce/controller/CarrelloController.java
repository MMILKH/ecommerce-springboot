package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import com.ecommerce.ecommerce.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody RigaCarrelloDto dto) {

        String response = carrelloService.addFromCatalog(dto);
        if (response.equals("ok")) {
            return ResponseEntity.ok(dto.toString());
        }
        return ResponseEntity.badRequest().body("non è stato possibile aggiungere la riga al carrello");

    }

    @GetMapping("/{utenteId}")
    public ResponseEntity<List<RigaCarrelloDto>> getCart(@PathVariable Integer utenteId) {

        CarrelloDto c = carrelloService.getActiveCart(utenteId);
        if (c != null) {
            List<RigaCarrelloDto> righe = carrelloService.getRowByCartId(c.getId());
            return ResponseEntity.ok(righe);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{rigaId}")
    public ResponseEntity<RigaCarrelloDto> updateCartRow(@RequestBody RigaCarrelloDto dto,@PathVariable int rigaId) {

        try{
            if (carrelloService.existRiga(rigaId)) {
                RigaCarrelloDto nuovaRiga = carrelloService.updateRowQuantity(rigaId, dto.getQuantita());
                return ResponseEntity.ok(nuovaRiga);
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @DeleteMapping("/{rigaId}")
    public ResponseEntity<String> deleteCartRow(@PathVariable int rigaId) {

        try{
            if (carrelloService.existRiga(rigaId)) {carrelloService.deleteRow(rigaId);
                return ResponseEntity.ok("Riga con id "+ rigaId + "Eliminata");
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Impossibile eseguire la richiesta");
        }

    }



}
