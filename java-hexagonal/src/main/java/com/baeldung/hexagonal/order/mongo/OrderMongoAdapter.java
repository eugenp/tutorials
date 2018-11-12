package com.baeldung.hexagonal.order.mongo;

import com.baeldung.hexagonal.order.Order;
import com.baeldung.hexagonal.order.persistence.OrderPersistence;
import org.springframework.stereotype.Service;

@Service
public class OrderMongoAdapter implements OrderPersistence {
    private OrderMongoRepository orderMongoRepository;

    public OrderMongoAdapter(OrderMongoRepository orderMongoRepository) {
        this.orderMongoRepository = orderMongoRepository;
    }

    @Override
    public void save(Order order) {
        orderMongoRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderMongoRepository.delete(order);
    }
}
