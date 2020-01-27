package com.baeldung.dddhexagonalspring.infrastracture.repository;

import java.util.Optional;
import java.util.UUID;

import com.baeldung.dddhexagonalspring.domain.Order;
import com.baeldung.dddhexagonalspring.domain.repository.OrderRepository;

public class CassandraDbOrderRepository implements OrderRepository {

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
