package com.baeldung.hexagonal.domain.repository;

import com.baeldung.hexagonal.domain.Order;

public interface OrderRepository {
    Order findById(Long id);

    Order save(Order order);
}
