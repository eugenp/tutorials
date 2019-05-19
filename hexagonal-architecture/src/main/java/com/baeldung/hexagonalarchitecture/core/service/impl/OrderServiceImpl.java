package com.baeldung.hexagonalarchitecture.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalarchitecture.core.domain.Order;
import com.baeldung.hexagonalarchitecture.core.repository.OrderRepository;
import com.baeldung.hexagonalarchitecture.core.service.NotificationService;
import com.baeldung.hexagonalarchitecture.core.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.getOrderById(id);
    }

    @Override
    public Order createOrder(Order order) {
        Order createdOrder = orderRepository.createOrder(order);
        notificationService.notifyOrderCreation(createdOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order updatedOrder = orderRepository.updateOrder(id, order);
        notificationService.notifyOrderUpdation(updatedOrder);
        return updatedOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.deleteOrder(id);
        notificationService.notifyOrderDeletion(order);
    }

}
