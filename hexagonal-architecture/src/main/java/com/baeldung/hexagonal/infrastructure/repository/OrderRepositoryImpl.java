package com.baeldung.hexagonal.infrastructure.repository;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.repository.OrderRepository;
import com.baeldung.hexagonal.infrastructure.builder.OrderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private SpringDataJpaOrderRepository jpaOrderRepository;


    @Override
    public Order findById(Long id) {
        Optional<OrderEntity> orderEntity = jpaOrderRepository.findById(id);
        if(orderEntity.isPresent()) {
            return OrderBuilder.fromEntitytoDomain(orderEntity.get());
        }
        else throw new EntityNotFoundException("Order with ID " + id + " not found");
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = OrderBuilder.fromDomainToEntity(order);
        orderEntity = jpaOrderRepository.save(orderEntity);
        order.setId(orderEntity.getId());
        return order;
    }
}
