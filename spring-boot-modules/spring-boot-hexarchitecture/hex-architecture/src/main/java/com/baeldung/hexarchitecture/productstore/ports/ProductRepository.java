package com.baeldung.hexarchitecture.productstore.ports;

import com.baeldung.hexarchitecture.productstore.core.domain.model.Product;

public interface ProductRepository {

    void createProduct(Product product);

    Product getProduct(String id);
}
