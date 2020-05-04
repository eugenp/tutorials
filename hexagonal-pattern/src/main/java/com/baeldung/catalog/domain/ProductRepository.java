package com.baeldung.catalog.domain;

import java.util.Optional;

public interface ProductRepository {
    public Optional<Product> findById(String id);

    public void save(Product product);
}
