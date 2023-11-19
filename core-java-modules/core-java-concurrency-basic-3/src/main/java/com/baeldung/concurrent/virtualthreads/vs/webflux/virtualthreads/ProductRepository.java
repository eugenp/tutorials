package com.baeldung.concurrent.virtualthreads.vs.webflux.virtualthreads;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.concurrent.virtualthreads.vs.webflux.model.Product;

interface ProductRepository extends MongoRepository<Product, String> {
}