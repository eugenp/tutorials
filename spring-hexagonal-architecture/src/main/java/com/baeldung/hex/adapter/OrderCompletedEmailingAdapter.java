package com.baeldung.hex.adapter;

import com.baeldung.hex.core.Order;
import com.baeldung.hex.port.OrderCompletedPort;

public class OrderCompletedEmailingAdapter implements OrderCompletedPort {
    @Override
    public void orderCompleted(Order order) {
        System.err.println("Email order complete message to " + order.getOrderedBy());
    }
}
