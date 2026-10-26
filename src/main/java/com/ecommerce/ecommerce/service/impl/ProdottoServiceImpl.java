package com.ecommerce.ecommerce.service.impl;
import com.ecommerce.ecommerce.dto.ProdottoDto;
import com.ecommerce.ecommerce.entity.Prodotto;
import com.ecommerce.ecommerce.repository.ProdottoRepository;
import com.ecommerce.ecommerce.service.ProdottoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProdottoServiceImpl implements ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ProdottoDto> showAll() {
        List<Prodotto> prodotti = prodottoRepository.findAll();
        List<ProdottoDto> prodottiDto = new ArrayList<>();
        for (Prodotto prodotto : prodotti) {
            prodottiDto.add(modelMapper.map(prodotto, ProdottoDto.class));
        }
        return prodottiDto;
    }

    public List<ProdottoDto> findProducts(String categoria) {
        List<Prodotto> products;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Prodotto> cq = cb.createQuery(Prodotto.class);
        Root<Prodotto> root = cq.from(Prodotto.class);

        if (categoria != null && !categoria.isBlank()) {
            Predicate predicateCategoria = cb.equal(root.get("categoria"),categoria);
            cq.select(root).where(predicateCategoria);
        }
        else{
            cq.select(root);
        }

        products = entityManager.createQuery(cq).getResultList();
        return products.stream()
                .map(prodotto -> modelMapper.map(prodotto, ProdottoDto.class))
                .toList();
    }



}
