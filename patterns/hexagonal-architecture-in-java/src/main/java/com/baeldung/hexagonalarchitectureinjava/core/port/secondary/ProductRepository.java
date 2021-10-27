package com.baeldung.hexagonalarchitectureinjava.core.port.secondary;

import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    void save(Product product);

    Product findById(UUID id);

    List<Product> findAll();
}
