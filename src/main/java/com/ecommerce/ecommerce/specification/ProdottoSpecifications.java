package com.ecommerce.ecommerce.specification;

import com.ecommerce.ecommerce.entity.Prodotto;
import org.springframework.data.jpa.domain.Specification;


public class ProdottoSpecifications {

    public static Specification<Prodotto> containsCategoria(String categoria) {
        return (root, query, cb) ->
                (categoria == null || categoria.trim().isEmpty()) ? null :
                        cb.like(cb.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%");
    }

    public static Specification<Prodotto> containsNome(String nome) {
        return (root, query, cb) ->
                (nome == null || nome.trim().isEmpty()) ? null :
                        cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Prodotto> containsDescrizioneBreve(String descrizione) {
        return (root, query, cb) ->
                (descrizione == null || descrizione.trim().isEmpty()) ? null :
                        cb.like(cb.lower(root.get("descrizioneBreve")), "%" + descrizione.toLowerCase() + "%");
    }

    public static Specification<Prodotto> hasSku(String sku) {
        return (root, query, cb) ->
                (sku == null || sku.trim().isEmpty()) ? null :
                        cb.equal(root.get("sku"), sku);
    }
}
