package com.baeldung.mockitospytest;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

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
