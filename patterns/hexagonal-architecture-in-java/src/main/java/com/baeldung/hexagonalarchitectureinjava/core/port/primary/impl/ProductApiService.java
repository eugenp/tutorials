package com.baeldung.hexagonalarchitectureinjava.core.port.primary.impl;

import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;
import com.baeldung.hexagonalarchitectureinjava.core.port.primary.ProductApi;
import com.baeldung.hexagonalarchitectureinjava.core.port.secondary.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductApiService implements ProductApi {

    private final ProductRepository productRepository;

    @Autowired
    public ProductApiService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public List<Product> all() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Product product = this.productRepository.findById(id);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(product);
    }
}
