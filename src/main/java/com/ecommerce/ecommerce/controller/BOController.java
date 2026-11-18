package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.api.AdminApi;
import com.ecommerce.ecommerce.api.model.PaginatedResponseContentInner;
import com.ecommerce.ecommerce.api.model.ProdottoDto;
import com.ecommerce.ecommerce.api.model.PaginatedResponse;
import com.ecommerce.ecommerce.api.model.UtenteDto;
import com.ecommerce.ecommerce.service.ProdottoService;
import com.ecommerce.ecommerce.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class BOController implements AdminApi {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ProdottoService prodottoService;




    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(Integer id) throws Exception {
        prodottoService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(String id) throws Exception {
        utenteService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PaginatedResponse> searchProductsAdmin(String nome, String categoria, String descrizioneBreve, String sku, Integer page, Integer elements, String sort) throws Exception {
        int p = (page != null) ? page : 0;
        int e = (elements != null) ? elements : 10;
        String sortBy = "nome";
        Sort.Direction direction = Sort.Direction.ASC;

        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            sortBy = parts[0].trim();

            if (parts[1].trim().equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
        }

        Sort s = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(p,e,s);
        Page<ProdottoDto> paginaCorrente = prodottoService.searchProductsAdmin(nome, categoria, descrizioneBreve, sku,pageable);
        PaginatedResponse response = new PaginatedResponse();

        List<PaginatedResponseContentInner> paginatedResponseContentInners = new ArrayList<>(paginaCorrente.getContent());

        response.setContent(paginatedResponseContentInners);
        response.setTotalPages(paginaCorrente.getTotalPages());
        response.setTotalElements((int) paginaCorrente.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UtenteDto>> searchUserAdmin(String email, String nome, String cognome, String numero, String ruolo,LocalDate dataInizio, LocalDate dataFine) throws Exception {
        List<UtenteDto> utenti = utenteService.getUsers(nome, cognome, email, numero, dataInizio, dataFine, ruolo);
        return ResponseEntity.ok().body(utenti);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdottoDto> updateProduct(Integer id, ProdottoDto prodottoDto) throws Exception {
        ProdottoDto response = prodottoService.updateProduct(id, prodottoDto);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UtenteDto> updateUser(Integer id, UtenteDto utenteDto) throws Exception {
        UtenteDto response = utenteService.updateUser(id, utenteDto);
        return ResponseEntity.ok().body(response);
    }


}