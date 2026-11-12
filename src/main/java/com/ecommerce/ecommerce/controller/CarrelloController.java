package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.CartsApi;
import com.ecommerce.ecommerce.api.model.CarrelloDto;
import com.ecommerce.ecommerce.api.model.RigaCarrelloDto;
import com.ecommerce.ecommerce.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cart")

public class CarrelloController implements CartsApi {
    @Autowired
    private CarrelloService carrelloService;

    @Override
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<RigaCarrelloDto> addToCart(Integer cartId, RigaCarrelloDto dto) {
        dto = carrelloService.addFromCatalog(dto, cartId);
        return ResponseEntity.ok(dto);
    }

    @Override
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<CarrelloDto> getCart(Integer userId) {
        CarrelloDto c = carrelloService.getActiveCart(userId);
        List<RigaCarrelloDto> righe = carrelloService.getRowByCartId(c.getId());
        c.setRighe(righe);
        return ResponseEntity.ok(c);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Override
    public ResponseEntity<RigaCarrelloDto> updateRow(Integer rowId, RigaCarrelloDto dto) {
        RigaCarrelloDto nuovaRiga = carrelloService.updateRowQuantity(rowId, dto.getQuantita());
        return ResponseEntity.ok(nuovaRiga);
    }

    @Override
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Void> deleteRow(Integer rowId) {
        carrelloService.deleteRow(rowId);
        return ResponseEntity.noContent().build();
    }

}
