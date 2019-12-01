package com.baeldung.patterns.hexagonal.infrastructure.adapter;

import com.baeldung.patterns.hexagonal.domain.model.Product;
import com.baeldung.patterns.hexagonal.domain.port.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private Map<String, Product> inMemoryStore = new HashMap<String, Product>();

    public void addProducts(List<Product> products) {
        products.stream()
                .forEach(product -> inMemoryStore.put(product.getId(), product));
    }

    public Product findProduct(String id) {
        return inMemoryStore.get(id);
    }
}
