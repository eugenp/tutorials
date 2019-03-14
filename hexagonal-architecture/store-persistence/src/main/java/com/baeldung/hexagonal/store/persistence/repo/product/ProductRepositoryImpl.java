package com.baeldung.hexagonal.store.persistence.repo.product;

import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.ProductDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductDataStore {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return this.productRepository.findById(productId);
    }
}