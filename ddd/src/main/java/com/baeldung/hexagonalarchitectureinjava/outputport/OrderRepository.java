package com.baeldung.hexagonalarchitectureinjava.outputport;

import java.util.UUID;

public interface OrderRepository {

    OrderEntity findOrderById(UUID orderId);

    OrderEntity saveOrder(OrderEntity orderEntity);

    boolean removeOrder(UUID id);
}
