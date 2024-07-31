package com.baeldung.spring.reactive.performance.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.baeldung.spring.reactive.performance.model.Product;

interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}