package com.baeldung.pattern.architecture.hexagonal.service;

import com.baeldung.pattern.architecture.hexagonal.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    void saveProduct(Product product);

    Product getById(Long id);
}
