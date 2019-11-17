package com.baeldung.ddd.layers.infrastracture.repository;

import com.baeldung.ddd.layers.domain.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataOrderRepository extends MongoRepository<Order, ObjectId> {
}
