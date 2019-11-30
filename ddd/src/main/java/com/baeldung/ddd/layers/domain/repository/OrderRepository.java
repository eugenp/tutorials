package com.baeldung.ddd.layers.domain.repository;

import com.baeldung.ddd.layers.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID id);

    void save(Order order);
}
