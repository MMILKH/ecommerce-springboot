package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.CartsApi;
import com.ecommerce.ecommerce.api.OrdersApi;
import com.ecommerce.ecommerce.api.model.OrdineDto;
import com.ecommerce.ecommerce.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrdineController implements OrdersApi {
    @Autowired
    private OrdineService ordineService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<OrdineDto> checkout(Integer userId,OrdineDto ordine) {
        OrdineDto response = ordineService.createOrder(ordine,userId);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
