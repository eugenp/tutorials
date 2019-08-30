package com.baeldung.hex.core;

public class Order {
    String item;
    String orderedBy;
    int quantity;
    long createdDate;
    long completedDate;

    public Order(String item, String orderedBy, int quantity) {
        createdDate = System.currentTimeMillis();
        this.orderedBy = orderedBy;
        this.item = item;
        this.quantity = quantity;
    }

    public Order(String item, String orderedBy) {
        this(orderedBy, item, 1);
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getCompletedDate() {
        return completedDate;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void complete() {
        this.completedDate = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Order{" +
                "item='" + item + '\'' +
                ", orderedBy='" + orderedBy + '\'' +
                ", quantity=" + quantity +
                ", createdDate=" + createdDate +
                ", completedDate=" + completedDate +
                '}';
    }
}
