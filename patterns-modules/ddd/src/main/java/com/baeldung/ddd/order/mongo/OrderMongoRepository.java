package com.baeldung.ddd.order.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.ddd.order.Order;

public interface OrderMongoRepository extends MongoRepository<Order, String> {

}
