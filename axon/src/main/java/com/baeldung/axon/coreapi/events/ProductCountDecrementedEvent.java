package com.baeldung.axon.coreapi.events;

import java.util.Objects;

public class ProductCountDecrementedEvent {

    private final String orderId;
    private final String productId;

    public ProductCountDecrementedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCountDecrementedEvent that = (ProductCountDecrementedEvent) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public String toString() {
        return "ProductCountDecrementedEvent{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
