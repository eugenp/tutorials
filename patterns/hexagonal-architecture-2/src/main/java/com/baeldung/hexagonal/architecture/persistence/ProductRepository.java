package com.baeldung.hexagonal.architecture.persistence;

import com.baeldung.hexagonal.architecture.domain.Product;

public interface ProductRepository {

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
