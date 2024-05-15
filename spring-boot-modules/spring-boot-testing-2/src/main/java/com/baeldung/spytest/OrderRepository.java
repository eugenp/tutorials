package com.baeldung.spytest;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

    public static final HashMap<UUID, Order> orders = new HashMap<>();

    public Order save(Order order) {
        UUID orderId = UUID.randomUUID();
        order.setId(orderId);
        orders.put(UUID.randomUUID(), order);
        return order;
    }
}
