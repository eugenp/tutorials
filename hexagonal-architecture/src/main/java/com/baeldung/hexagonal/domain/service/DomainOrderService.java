package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.repository.OrderRepository;

public class DomainOrderService implements OrderService {

    private OrderRepository orderRepository;

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return this.orderRepository.findById(id);
    }
}
