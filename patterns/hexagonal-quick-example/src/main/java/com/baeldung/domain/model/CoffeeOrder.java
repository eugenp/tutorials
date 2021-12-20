package com.baeldung.domain.model;

import java.util.Date;

public class CoffeeOrder {

    private int id;
    private CoffeeType type;
    private OrderStatus status;
    private Date orderDate;
    private String orderAddress;

    public int getId() {
        return id;
    }

    public CoffeeOrder setId(int id) {
        this.id = id;
        return this;
    }

    public CoffeeType getType() {
        return type;
    }

    public CoffeeOrder setType(CoffeeType type) {
        this.type = type;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public CoffeeOrder setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public CoffeeOrder setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public CoffeeOrder setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
        return this;
    }

    public boolean updateStatus(OrderStatus status) {
        if (this.status == OrderStatus.PENDING) {
            this.status = status;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "CoffeeOrder{" + "id=" + id + ", type=" + type + ", status=" + status + ", orderDate=" + orderDate + ", orderAddress='" + orderAddress + '\'' + '}';
    }
}
