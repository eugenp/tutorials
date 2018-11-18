package com.baeldung.hexagonal;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<Product> findProductsCheaperThan(Product product) {
        return productRepository.findByName(product.getName())
                .filter(p -> p.getPrice() < product.getPrice())
                .collect(Collectors.toList());
    }
}