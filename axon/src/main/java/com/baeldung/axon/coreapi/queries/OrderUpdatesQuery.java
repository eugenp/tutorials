package com.baeldung.axon.coreapi.queries;

public class OrderUpdatesQuery {
    private final String orderId;

    public OrderUpdatesQuery(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
