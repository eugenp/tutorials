package com.baeldung.hexagonal.store.persistence.repo.order;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.OrderDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderDataStore {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return this.orderRepository.findById(orderId);
    }

    @Override
    public Iterable<Order> findAll() {
        return this.orderRepository.findAll();
    }
}