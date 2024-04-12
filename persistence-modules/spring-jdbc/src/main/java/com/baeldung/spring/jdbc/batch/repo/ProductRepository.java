package com.baeldung.spring.jdbc.batch.repo;

import com.baeldung.spring.jdbc.batch.model.Product;

import java.util.List;

public interface ProductRepository {
    void saveAll(List<Product> products);
}
