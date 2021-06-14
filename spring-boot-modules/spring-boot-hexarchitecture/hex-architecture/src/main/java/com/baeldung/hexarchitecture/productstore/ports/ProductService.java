package com.baeldung.hexarchitecture.productstore.ports;

import com.baeldung.hexarchitecture.productstore.core.domain.model.Product;

public interface ProductService {
        void createProduct(Product product);

        Product getProduct(String id);
}