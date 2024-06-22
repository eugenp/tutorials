package com.baeldung.spring.kafka.delay;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    HashMap<UUID, Order> orders = new HashMap<>();

    public Status findStatusById(UUID orderId) {
        return Status.ORDER_CONFIRMED;
    }

    public void processOrder(Order order) {
        order.setOrderProcessedTime(LocalDateTime.now());
        orders.put(order.getOrderId(), order);
    }

    public Map<UUID, Order> getOrders() {
        return orders;
    }

    enum Status {
        CREATED,
        ORDER_CONFIRMED,
        ORDER_PROCESSED,
        DELETED
    }
}
