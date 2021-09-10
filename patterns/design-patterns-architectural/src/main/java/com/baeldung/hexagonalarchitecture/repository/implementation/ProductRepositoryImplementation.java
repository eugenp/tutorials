package com.baeldung.hexagonalarchitecture.repository.implementation;

import com.baeldung.hexagonalarchitecture.domain.Product;
import com.baeldung.hexagonalarchitecture.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductRepositoryImplementation implements ProductRepository {

    Map<UUID, Product> database = new HashMap<>();

    @Override
    public Product getProductById(UUID productId) {
        return database.getOrDefault(productId, null);
    }

    @Override
    public void save(Product product) {
        database.putIfAbsent(product.getId(), product);
    }
}
