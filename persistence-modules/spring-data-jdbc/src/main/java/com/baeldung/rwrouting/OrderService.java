package com.baeldung.rwrouting;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllReadOnly() {
        return orderRepository.findAll();
    }

    @Transactional
    public List<Order> findAllReadWrite() {
        return orderRepository.findAll();
    }
}
