package com.baeldung.boot.hexagonal.core.port;

import com.baeldung.boot.hexagonal.core.domain.Product;

import java.util.List;

public interface ProductRepo {

    void addProduct(Product product);
    Product getProduct(Long productId);
    List allProducts();

}

