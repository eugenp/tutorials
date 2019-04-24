package com.baeldung.persistence.model;

import org.springframework.hateoas.ResourceSupport;

public class Order extends ResourceSupport {
    private String orderId;
    private double price;
    private int quantity;

    public Order() {
        super();
    }

    public Order(final String orderId, final double price, final int quantity) {
        super();
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", price=" + price + ", quantity=" + quantity + "]";
    }

}