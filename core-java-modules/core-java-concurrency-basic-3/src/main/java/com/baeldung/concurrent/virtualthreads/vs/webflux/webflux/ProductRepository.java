package com.baeldung.concurrent.virtualthreads.vs.webflux.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.concurrent.virtualthreads.vs.webflux.model.Product;

interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}