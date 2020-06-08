package com.baeldung.hexagonal.repository.mongodb;

import com.baeldung.hexagonal.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMongoDbOrderRepository extends MongoRepository<Order, UUID> {
}
