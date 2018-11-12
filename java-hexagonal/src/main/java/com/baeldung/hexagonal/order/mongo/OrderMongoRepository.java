package com.baeldung.hexagonal.order.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.hexagonal.order.Order;

public interface OrderMongoRepository extends MongoRepository<Order, String> {

}
