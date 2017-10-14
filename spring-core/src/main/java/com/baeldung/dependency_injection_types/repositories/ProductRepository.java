package com.baeldung.dependency_injection_types.repositories;

import com.baeldung.dependency_injection_types.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private List<Product> products = new ArrayList<>();

    public List<Product> findAll() {
        return products;
    }

    public void add(Product product) {
        products.add(product);
    }

}
