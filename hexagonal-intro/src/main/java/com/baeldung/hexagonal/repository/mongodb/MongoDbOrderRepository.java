package com.baeldung.hexagonal.repository.mongodb;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MongoDbOrderRepository implements OrderRepository {

    private final SpringDataMongoDbOrderRepository orderRepository;

    @Autowired
    public MongoDbOrderRepository(SpringDataMongoDbOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }


}
