package com.baeldung.hexagonal.infrastructure.builder;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.infrastructure.repository.OrderEntity;

public class OrderBuilder {

    public static OrderEntity fromDomainToEntity(Order order) {
        return new OrderEntity(order.getId(), order.getPrice());
    }

    public static Order fromEntitytoDomain(OrderEntity orderEntity) {
        return new Order(orderEntity.getId(), orderEntity.getPrice());
    }
}
