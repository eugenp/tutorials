package com.baeldung.pattern.hexagonal.product.adapter.outbound.persistence;

import com.baeldung.pattern.hexagonal.product.application.port.outbound.LoadProductPort;
import com.baeldung.pattern.hexagonal.product.domain.Product;

public class SqlProductAdapter implements LoadProductPort {

    @Override
    public Product getById(String productId) {
        // return product using SQL
        throw new UnsupportedOperationException("not implemented yet");
    }
}
