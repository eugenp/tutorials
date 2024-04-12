package com.baeldung.spytest;

import java.util.UUID;

public class Order {

    private UUID id;

    private String name;

    private OrderType orderType;

    private double orderQuantity;

    private String address;

    public Order(UUID id, String name, double orderQuantity, String address) {
        this.id = id;
        this.name = name;
        this.orderQuantity = orderQuantity;
        this.address = address;
    }

    public enum OrderType {
        INDIVIDUAL, BULK;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getOrderQuantity() {
        return orderQuantity;
    }

    public String getAddress() {
        return address;
    }
}


