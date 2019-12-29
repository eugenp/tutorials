package com.baeldung.dddhexagonalspring.infrastracture.repository;

import com.baeldung.dddhexagonalspring.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataOrderRepository extends MongoRepository<Order, UUID> {
}
