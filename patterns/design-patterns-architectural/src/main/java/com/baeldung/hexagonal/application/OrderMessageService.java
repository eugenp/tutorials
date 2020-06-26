package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Order;

public class OrderMessageService {
    public void send(Order order) {
        System.out.println(order.getId());
    }
}