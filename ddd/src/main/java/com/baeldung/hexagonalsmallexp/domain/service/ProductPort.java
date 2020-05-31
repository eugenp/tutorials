package com.baeldung.hexagonalsmallexp.domain.service;

import com.baeldung.hexagonalsmallexp.domain.Product;

import java.util.UUID;

public interface ProductPort {

    void save(Product product);

    Product findByProductId(UUID productId);
}
