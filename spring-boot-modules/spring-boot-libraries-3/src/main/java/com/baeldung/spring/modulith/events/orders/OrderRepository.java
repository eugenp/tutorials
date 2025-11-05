package com.baeldung.spring.modulith.events.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public Order save(Order order) {
        order = new Order(UUID.randomUUID()
            .toString(), order.customerId(), order.productIds(), order.timestamp());
        orders.add(order);
        return order;
    }

    public List<Order> ordersByCustomer(String customerId) {
        return orders.stream()
            .filter(it -> it.customerId()
                .equals(customerId))
            .toList();
    }

}
