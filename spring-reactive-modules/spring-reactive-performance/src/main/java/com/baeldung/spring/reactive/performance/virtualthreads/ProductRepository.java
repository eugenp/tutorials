package com.baeldung.spring.reactive.performance.virtualthreads;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.spring.reactive.performance.model.Product;

interface ProductRepository extends MongoRepository<Product, String> {
}