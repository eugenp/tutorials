package com.baeldung.hexagonalarchitectureinjava.domain.beans;

import java.util.UUID;

public class ConfimrOrderRequest {
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    UUID orderId;
}
