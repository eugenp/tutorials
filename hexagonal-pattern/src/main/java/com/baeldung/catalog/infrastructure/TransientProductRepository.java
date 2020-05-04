package com.baeldung.catalog.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.baeldung.catalog.domain.Product;
import com.baeldung.catalog.domain.ProductRepository;

@ApplicationScoped
public class TransientProductRepository implements ProductRepository {
    private Map<String, Product> productMap = new HashMap<>();
    
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(productMap.get(id));
    }

    public void save(Product product) {
        productMap.put(product.getId(), product);
    }
}
