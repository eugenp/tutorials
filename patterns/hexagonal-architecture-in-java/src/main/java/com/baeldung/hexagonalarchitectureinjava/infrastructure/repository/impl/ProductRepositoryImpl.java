package com.baeldung.hexagonalarchitectureinjava.infrastructure.repository.impl;

import com.baeldung.hexagonalarchitectureinjava.core.domain.model.Product;
import com.baeldung.hexagonalarchitectureinjava.core.port.secondary.ProductRepository;
import com.baeldung.hexagonalarchitectureinjava.infrastructure.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<ProductEntity> products = new ArrayList<>();

    @Override
    public void save(Product product) {
        this.products.add(toEntity(product));
    }

    @Override
    public Product findById(UUID id) {
        Optional<ProductEntity> productEntity = this.products.stream()
            .filter(p -> p.getId()
                .equals(id))
            .findFirst();
        if (productEntity.isPresent()) {
            return toProduct(productEntity.get());
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return this.products.stream()
            .map(this::toProduct)
            .collect(Collectors.toList());
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getCategory(), product.getPrice());
    }

    private Product toProduct(ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getCategory(), productEntity.getPrice());
    }
}
