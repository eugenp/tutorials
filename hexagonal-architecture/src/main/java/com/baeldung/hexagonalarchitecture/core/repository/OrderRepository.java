package com.baeldung.hexagonalarchitecture.core.repository;

import com.baeldung.hexagonalarchitecture.core.domain.Order;

public interface OrderRepository {

    Order getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Long id, Order order);

    Order deleteOrder(Long id);

}
