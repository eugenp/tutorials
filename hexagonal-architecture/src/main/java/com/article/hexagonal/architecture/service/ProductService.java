package com.article.hexagonal.architecture.service;

import java.util.List;

import com.article.hexagonal.architecture.model.Product;

/**
 * @author AshwiniKeshri
 *
 */

public interface ProductService {

    List<Product> findAll();

    Product findById(long id);

    Long create(Product product);

    void saveAll(List<Product> products);
}
