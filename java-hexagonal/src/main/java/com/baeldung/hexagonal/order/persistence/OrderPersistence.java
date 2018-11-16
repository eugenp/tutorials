package com.baeldung.hexagonal.order.persistence;

import com.baeldung.hexagonal.order.Order;

public interface OrderPersistence {
    void save(Order order);

    void delete(Order order);
}
