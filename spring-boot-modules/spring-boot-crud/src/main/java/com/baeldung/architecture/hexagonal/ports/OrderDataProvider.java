package com.baeldung.architecture.hexagonal.ports;

import java.util.List;

import com.baeldung.architecture.hexagonal.core.Order;

public interface OrderDataProvider {

    public Order saveOrder(Order order);

    public Order getOrderById(Long orderId);

    public List<Order> getAllOrders();

}
