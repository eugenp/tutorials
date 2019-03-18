package com.baeldung.hexagonal.store.core.context.order.infrastructure;

import com.baeldung.hexagonal.store.core.context.order.entity.Product;

import java.util.Optional;

public interface ProductDataStore {
    Product save(Product product);

    Optional<Product> findById(Long productId);
}
