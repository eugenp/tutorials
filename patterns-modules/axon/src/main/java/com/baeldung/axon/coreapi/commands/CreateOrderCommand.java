package com.baeldung.axon.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;

    public CreateOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateOrderCommand that = (CreateOrderCommand) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "CreateOrderCommand{" + "orderId='" + orderId + '\'' + '}';
    }
}