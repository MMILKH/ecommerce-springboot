package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.api.model.ProdottoDto;
import com.ecommerce.ecommerce.entity.Prodotto;
import com.ecommerce.ecommerce.repository.ProdottoRepository;
import com.ecommerce.ecommerce.service.ProdottoService;
import com.ecommerce.ecommerce.specification.ProdottoSpecifications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProdottoServiceImpl implements ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private ModelMapper modelMapper;


//    @Override
//    public List<ProdottoDto> showAll() {
//        List<Prodotto> prodotti = prodottoRepository.findAll();
//        List<ProdottoDto> prodottiDto = new ArrayList<>();
//        for (Prodotto prodotto : prodotti) {
//            prodottiDto.add(modelMapper.map(prodotto, ProdottoDto.class));
//        }
//        return prodottiDto;
//    }

    public List<ProdottoDto> findProducts(String categoria) {
        List<Prodotto> prodotti;
        if (categoria != null && !categoria.isBlank()) {
            Specification<Prodotto> spec = ProdottoSpecifications.containsCat(categoria);
            prodotti = prodottoRepository.findAll(spec);
        } else {
            prodotti = prodottoRepository.findAll();
        }
        return prodotti.stream()
                .map(p -> modelMapper.map(p, ProdottoDto.class))
                .toList();
    }
}
