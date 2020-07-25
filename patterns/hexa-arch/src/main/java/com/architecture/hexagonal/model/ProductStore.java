package com.architecture.hexagonal.model;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductStore {

    private static Map<Integer, Product> productStore = Map.of(1, new Product(1, "Product 1", "This is a product description for product 1", 12.99),
            2, new Product(2, "Product 2", "This is a product description for product 2", 15.99));


    public Product findProductById(int productId) {
        return productStore.get(productId);
    }
}
