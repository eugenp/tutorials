package com.baeldung.architecture.hexagonal.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.architecture.hexagonal.ports.OrderDataProvider;
import com.baeldung.architecture.hexagonal.ports.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDataProvider orderDataProvider;

    @Override
    public Order createOrder(Order order) {
        // perform any business rule here and then save the order
        return orderDataProvider.saveOrder(order);
    }

    @Override
    public Order getOrder(Long orderId) {
        // perform any business rule here if required
        return orderDataProvider.getOrderById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        // perform any business rule here if required
        return orderDataProvider.getAllOrders();
    }

}
