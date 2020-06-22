package com.baeldung.architecture.hexagonal.ports;

import java.util.List;

import com.baeldung.architecture.hexagonal.core.Order;

public interface OrderService {

    public Order createOrder(Order order);

    public Order getOrder(Long orderId);

    public List<Order> getAllOrders();

}
