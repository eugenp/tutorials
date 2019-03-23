package com.baeldung.hexagonal.store.infrastructure.persistence.repo;

import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.OrderDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class OrderRepositoryImpl implements OrderDataStore {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }
}

@Repository
interface OrderRepository extends CrudRepository<Order, Long> {
}