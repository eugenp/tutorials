package com.baeldung.reactive.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.baeldung.domain.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, ObjectId> {

}
