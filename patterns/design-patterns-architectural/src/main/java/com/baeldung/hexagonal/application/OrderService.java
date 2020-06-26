package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.OrderRepository;
import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.application.OrderMessageService;

public class OrderService {
    public Order createOrder(String sku) {
        OrderRepository orderRepository = new OrderRepository();
        Order order = orderRepository.createOrder(sku);

        OrderMessageService orderMessageService = new OrderMessageService();
        orderMessageService.send(order);

        return order;
    }
}