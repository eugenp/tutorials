package com.baeldung.hibernate.service;

import com.baeldung.hibernate.entities.Product;
import com.baeldung.hibernate.repository.ProductRepository;
import reactor.core.publisher.*;
import  org.springframework.data.repository.reactive.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    public Mono<Void> deleteById(Long id) {
        return productRepository.deleteById(id);
    }

}