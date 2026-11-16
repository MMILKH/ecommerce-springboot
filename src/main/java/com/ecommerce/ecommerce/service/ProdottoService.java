package com.ecommerce.ecommerce.service;



import com.ecommerce.ecommerce.api.model.ProdottoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdottoService {

    List<ProdottoDto> findProducts(String categoria);
    void deleteProduct(int id);
    Page<ProdottoDto> searchProductsAdmin(String nome, String categoria, String descrizione, String sku, Pageable pageable) ;
    ProdottoDto updateProduct(int id, ProdottoDto prodottoDto);


    }
