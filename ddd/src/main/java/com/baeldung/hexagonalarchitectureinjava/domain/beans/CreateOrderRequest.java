package com.baeldung.hexagonalarchitectureinjava.domain.beans;

import com.baeldung.hexagonalarchitectureinjava.outputport.OrderEntity;

public class CreateOrderRequest {

    private OrderEntity orderEntity;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
