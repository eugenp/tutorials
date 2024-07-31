package com.baeldung.axon.coreapi.events;

import java.util.Objects;

public class OrderConfirmedEvent {

    private final String orderId;

    public OrderConfirmedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OrderConfirmedEvent other = (OrderConfirmedEvent) obj;
        return Objects.equals(this.orderId, other.orderId);
    }

    @Override
    public String toString() {
        return "OrderConfirmedEvent{" + "orderId='" + orderId + '\'' + '}';
    }
}
