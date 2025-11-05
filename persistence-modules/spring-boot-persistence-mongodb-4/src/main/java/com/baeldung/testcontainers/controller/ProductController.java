package com.baeldung.testcontainers.controller;

import com.baeldung.testcontainers.model.Product;
import com.baeldung.testcontainers.repository.ProductRepository;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public String createProduct(@RequestBody Product product) {
        return productRepository.save(product)
            .getId();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
