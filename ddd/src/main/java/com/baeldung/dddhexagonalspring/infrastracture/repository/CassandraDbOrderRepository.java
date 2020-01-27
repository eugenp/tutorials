package com.baeldung.dddhexagonalspring.infrastracture.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalspring.domain.Order;
import com.baeldung.dddhexagonalspring.domain.repository.OrderRepository;

@Component
public class CassandraDbOrderRepository implements OrderRepository {

    private final SpringDataCassandraOrderRepository orderRepository;
    
    @Autowired
    public CassandraDbOrderRepository(SpringDataCassandraOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Order order) {
        // TODO Auto-generated method stub
        
    }

}
