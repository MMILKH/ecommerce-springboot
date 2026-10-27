package com.ecommerce.ecommerce.specification;

import com.ecommerce.ecommerce.entity.Prodotto;
import org.springframework.data.jpa.domain.Specification;

public class ProdottoSpecifications {

    public static Specification<Prodotto> containsCat(String categoria) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("categoria")), categoria.toLowerCase());
    }
}
