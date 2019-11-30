package com.baeldung.ddd.layers.infrastracture.repository;

import com.baeldung.ddd.layers.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataOrderRepository extends MongoRepository<Order, UUID> {
}
