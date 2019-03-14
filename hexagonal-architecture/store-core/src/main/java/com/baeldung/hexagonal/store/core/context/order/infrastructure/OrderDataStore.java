package com.baeldung.hexagonal.store.core.context.order.infrastructure;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;

import java.util.Optional;

public interface OrderDataStore {
    Order save(Order orderProduct);
    Optional<Order> findById(Long orderId);
    Iterable<Order> findAll();
}
