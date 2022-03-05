package com.baeldung.persistence;

import com.baeldung.domain.Product;

public interface ProductRepository {

    Product getProductById(Integer productId);

    Product addProduct(Product product);

    Product removeProduct(Integer productId);
}
