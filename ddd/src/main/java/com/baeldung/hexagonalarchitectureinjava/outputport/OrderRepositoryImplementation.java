package com.baeldung.hexagonalarchitectureinjava.outputport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public class OrderRepositoryImplementation implements OrderRepository {

    SpringDataMongoOrderEntityRepository entityRepository;

    @Autowired
    public OrderRepositoryImplementation(final SpringDataMongoOrderEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public OrderEntity findOrderById(UUID orderId) {
        return entityRepository.findById(orderId).orElse(null);
    }

    @Override
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return entityRepository.save(orderEntity);
    }

    @Override
    public boolean removeOrder(UUID id) {
        entityRepository.delete(
                Objects.requireNonNull(
                        entityRepository.findById(id).orElse(null)));
        return true;
    }
}
