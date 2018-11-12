package com.baeldung.hexagonal.order.mongo;

import com.baeldung.hexagonal.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMongoRepository extends MongoRepository<Order, String> {

}
