package com.baeldung.pattern.hexagonal.product.adapter.outbound.persistence;

import com.baeldung.pattern.hexagonal.product.application.port.outbound.LoadProductPort;
import com.baeldung.pattern.hexagonal.product.domain.Product;

public class JpaProductAdapter implements LoadProductPort {

    @Override
    public Product getById(String productId) {
        // return product using JPA
        throw new UnsupportedOperationException("not implemented yet");
    }
}
