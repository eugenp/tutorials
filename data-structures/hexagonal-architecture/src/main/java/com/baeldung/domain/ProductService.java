package com.baeldung.domain;

public interface ProductService {

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}