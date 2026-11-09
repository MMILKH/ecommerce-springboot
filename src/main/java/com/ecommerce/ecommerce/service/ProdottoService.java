package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.ProdottoDto;

import java.util.List;

public interface ProdottoService {

//    List<ProdottoDto> showAll();

    List<ProdottoDto> findProducts(String categoria);


}
