package com.baeldung.string.tostring;

import java.util.Arrays;

public class CustomerArrayToString extends Customer {
    private Order[] orders;

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer [orders=" + Arrays.toString(orders) + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + "]";
    }
}
