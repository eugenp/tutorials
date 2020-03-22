package com.baeldung.dddhexagonaljava.core.service;

import com.baeldung.dddhexagonaljava.core.domain.Order;
import com.baeldung.dddhexagonaljava.ports.NotificationService;

import java.text.MessageFormat;

public class OrderService {

    private NotificationService notificationService;

    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void createOrder(Order order) {
        //create order

        String message = MessageFormat.format("Created new order with id `{0}`.", order.getId());

        notificationService.sendNotification(order.getCustomer(), message);
    }
}