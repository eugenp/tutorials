package com.baeldung.hexagonalarchitecture.repository;

import com.baeldung.hexagonalarchitecture.domain.Product;

import java.util.UUID;

public interface ProductRepository {
    Product getProductById(UUID productId);
    void save(Product product);
}
