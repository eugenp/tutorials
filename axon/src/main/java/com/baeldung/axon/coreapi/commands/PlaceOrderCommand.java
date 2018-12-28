package com.baeldung.axon.coreapi.commands;

import java.util.Objects;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class PlaceOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String product;

    public PlaceOrderCommand(String orderId, String product) {
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
        final PlaceOrderCommand other = (PlaceOrderCommand) obj;
        return Objects.equals(this.orderId, other.orderId)
                && Objects.equals(this.product, other.product);
    }

    @Override
    public String toString() {
        return "PlaceOrderCommand{" +
                "orderId='" + orderId + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}