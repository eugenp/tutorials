package com.baeldung.dddhexagonalspring.infrastracture.repository;

import com.baeldung.dddhexagonalspring.domain.Order;
import com.baeldung.dddhexagonalspring.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class MongoDbOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository orderRepository;

    @Autowired
    public MongoDbOrderRepository(final SpringDataOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(final Order order) {
        orderRepository.save(order);
    }
}
