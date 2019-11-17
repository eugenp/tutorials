package com.baeldung.ddd.layers.domain.repository;

import com.baeldung.ddd.layers.domain.Order;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(ObjectId id);

    void save(Order order);
}
