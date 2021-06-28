package com.baeldung.pattern.architecture.hexagonal.repository;

import com.baeldung.pattern.architecture.hexagonal.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> list();

    void save(Product product);

    Product get(Long id);

}
