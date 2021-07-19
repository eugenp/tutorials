package com.baeldung.tostring;

public class CustomerComplexObjectToString extends Customer {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Customer [order=" + order + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + "]";
    }
}