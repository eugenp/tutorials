package com.baeldung.pattern.hexagonal.product.adapter.outbound.persistence;

import com.baeldung.pattern.hexagonal.product.application.port.outbound.LoadProductPort;
import com.baeldung.pattern.hexagonal.product.domain.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InMemoryProductAdapter implements LoadProductPort {

    private static final Map<String, Product> PRODUCTS;

    static {
        PRODUCTS = new HashMap<>();
        PRODUCTS.put("123", new Product("123", false, BigDecimal.ONE));
        PRODUCTS.put("789", new Product("789", true, BigDecimal.TEN));
    }

    @Override
    public Product getById(String productId) {
        return PRODUCTS.get(productId);
    }
}
