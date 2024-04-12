package com.baeldung.nullconversion;

import java.util.Objects;

public class Delivery {

    private String message;

    public Delivery(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Delivery delivery = (Delivery) o;

        return Objects.equals(message, delivery.message);
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    public static Delivery freeDelivery() {
        return new Delivery("Free delivery");
    }
    public static Delivery defaultDelivery() {
        return new Delivery("Default delivery");
    }
}
