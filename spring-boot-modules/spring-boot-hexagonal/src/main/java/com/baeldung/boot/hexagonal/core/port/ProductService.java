package com.baeldung.boot.hexagonal.core.port;

import com.baeldung.boot.hexagonal.core.domain.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    Product getProduct(Long productId);
    List<Product> allProducts();
}
