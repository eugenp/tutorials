package com.baeldung.hexagonalarchitectureinjava.outputport;

import java.util.UUID;

public class OrderEntity {
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    UUID orderId;
}
