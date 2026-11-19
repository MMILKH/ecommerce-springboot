package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.api.model.ProdottoDto;
import com.ecommerce.ecommerce.entity.Prodotto;
import com.ecommerce.ecommerce.repository.ProdottoRepository;
import com.ecommerce.ecommerce.repository.RigaCarrelloRepository;
import com.ecommerce.ecommerce.service.ProdottoService;
import com.ecommerce.ecommerce.specification.ProdottoSpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.util.List;
@Service
public class ProdottoServiceImpl implements ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private ModelMapper modelMapper;



    public List<ProdottoDto> findProducts(String categoria) {
        List<Prodotto> prodotti;
        if (categoria != null && !categoria.isBlank()) {
            Specification<Prodotto> spec = ProdottoSpecifications.containsCategoria(categoria);
            prodotti = prodottoRepository.findAll(spec);
        } else {
            prodotti = prodottoRepository.findAll();
        }
        return prodotti.stream()
                .map(p -> modelMapper.map(p, ProdottoDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteProduct(int  id) {

        Prodotto p = prodottoRepository.findById(id);

        if (p != null) {
            prodottoRepository.deleteById(id);
        }else  {
            System.out.println("Prodotto non trovato");
        }

    }


    @Override
    public Page<ProdottoDto> searchProductsAdmin(String nome, String categoria, String descrizione, String sku, Pageable pageable) {


        Specification<Prodotto> spec = Specification
                .where(ProdottoSpecifications.containsNome(nome))
                .and(ProdottoSpecifications.containsCategoria(categoria))
                .and(ProdottoSpecifications.containsDescrizioneBreve(descrizione))
                .and(ProdottoSpecifications.hasSku(sku));

        Page<Prodotto> prodotti = prodottoRepository.findAll(spec, pageable);
        return prodotti.map(prod -> modelMapper.map(prod, ProdottoDto.class));
    }

    @Override
    @Transactional
    public ProdottoDto updateProduct(int id, ProdottoDto prodottoDto) {
        Prodotto prodottoEsistente = prodottoRepository.findById(id);

        if (prodottoEsistente != null) {
            prodottoEsistente.setNome(prodottoDto.getNome());
            prodottoEsistente.setCategoria(prodottoDto.getCategoria());
            prodottoEsistente.setDescrizioneBreve(prodottoDto.getDescrizioneBreve());
            prodottoEsistente.setPrezzo(new BigDecimal(prodottoDto.getPrezzo()));

            prodottoRepository.save(prodottoEsistente);
            return modelMapper.map(prodottoEsistente, ProdottoDto.class);
        }
        return null;
    }
}
