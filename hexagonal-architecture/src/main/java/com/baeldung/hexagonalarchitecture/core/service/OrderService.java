package com.baeldung.hexagonalarchitecture.core.service;

import com.baeldung.hexagonalarchitecture.core.domain.Order;

public interface OrderService {

    Order getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

}
