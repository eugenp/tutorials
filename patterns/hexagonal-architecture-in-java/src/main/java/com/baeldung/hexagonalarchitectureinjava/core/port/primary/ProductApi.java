package com.baeldung.hexagonalarchitectureinjava.core.port.primary;

import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductApi {

    void  addProduct(Product product);

    List<Product> all();

    Optional<Product> findById(UUID id);
}
