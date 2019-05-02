package com.baeldung.axon.coreapi.events;

import java.util.Objects;

public class OrderPlacedEvent {

    private final String orderId;
    private final String product;

    public OrderPlacedEvent(String orderId, String product) {
        this.orderId = orderId;
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, product);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OrderPlacedEvent other = (OrderPlacedEvent) obj;
        return Objects.equals(this.orderId, other.orderId)
                && Objects.equals(this.product, other.product);
    }

    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId='" + orderId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}