package com.baeldung.hexagonalspringboot.port;

import java.util.List;

import com.baeldung.hexagonalspringboot.domain.Product;

public interface IProductSearchRepository {

    List<Product> findByCategory(String category);

    List<Product> findAll();
}
