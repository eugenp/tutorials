package com.baeldung.hexagonal.framework;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.application.OrderService;

public class OrderController {
    public Order createOrder(String sku) {
        OrderService orderService = new OrderService();
        return orderService.createOrder(sku);
    }
}