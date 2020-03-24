package com.baeldung.dddhexagonaljava.ports;

import com.baeldung.dddhexagonaljava.core.domain.Order;

public interface OrderService {
    void createOrder(Order order);
}
