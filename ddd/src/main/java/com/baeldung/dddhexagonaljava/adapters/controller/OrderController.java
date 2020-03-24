package com.baeldung.dddhexagonaljava.adapters.controller;

import com.baeldung.dddhexagonaljava.core.domain.Order;
import com.baeldung.dddhexagonaljava.ports.OrderService;

public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void createOrder(Order order) {
        orderService.createOrder(order);
    }
}
