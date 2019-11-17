package com.baeldung.ddd.layers.infrastracture.repository;

import com.baeldung.ddd.layers.domain.Order;
import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MongoDbOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository orderRepository;

    @Autowired
    public MongoDbOrderRepository(final SpringDataOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(final ObjectId id) {
        return orderRepository.findById(id);
    }

    @Override
    public void save(final Order order) {
        orderRepository.save(order);
    }
}
