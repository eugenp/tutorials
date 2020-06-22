package com.baeldung.architecture.hexagonal.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.architecture.hexagonal.core.Order;
import com.baeldung.architecture.hexagonal.ports.OrderDataProvider;

@Repository
public class OrderRepository implements OrderDataProvider {

    Map<Long, Order> data = new HashMap<>();

    @Override
    public Order saveOrder(Order order) {
        data.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        Order order = data.get(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return data.values()
            .stream()
            .collect(Collectors.toList());
    }

}
