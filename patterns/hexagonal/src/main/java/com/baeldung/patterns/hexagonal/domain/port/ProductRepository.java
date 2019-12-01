package com.baeldung.patterns.hexagonal.domain.port;

import com.baeldung.patterns.hexagonal.domain.model.Product;

import java.util.List;

public interface ProductRepository {
    void addProducts(List<Product> products);
    Product findProduct(String id);
}
