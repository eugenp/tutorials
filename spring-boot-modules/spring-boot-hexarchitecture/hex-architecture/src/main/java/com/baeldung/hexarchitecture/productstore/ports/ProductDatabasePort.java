package com.baeldung.hexarchitecture.productstore.ports;

import com.baeldung.hexarchitecture.productstore.domain.model.Product;

public interface ProductDatabasePort {

    void addProduct(Product product);

    Product getProduct(String id);
}
