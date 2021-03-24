package com.baeldung.hexagonalarchitectureinjava.domain.beans;

import java.util.UUID;

public class OrderResponse {

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    private UUID orderId;

}
