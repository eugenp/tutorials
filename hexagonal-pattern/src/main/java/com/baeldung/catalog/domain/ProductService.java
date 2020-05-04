package com.baeldung.catalog.domain;

public interface ProductService {
    void createProduct(String id, String shortName);

    Product getProductById(String id);

    void addPrice(String id, ProductPrice price);
}
