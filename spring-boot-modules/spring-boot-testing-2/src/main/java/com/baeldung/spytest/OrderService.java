package com.baeldung.spytest;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public final OrderRepository orderRepository;

    public final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public Order save(Order order) {
        order = orderRepository.save(order);
        notificationService.notify(order);
        if (!notificationService.raiseAlert(order)) {
            throw new RuntimeException("Alert not raised");
        }
        return order;
    }
}
