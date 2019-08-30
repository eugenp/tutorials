package com.baeldung.hex.adapter;

import com.baeldung.hex.core.Order;
import com.baeldung.hex.port.OrderCompletedPort;

public class OrderCompletedPrintingAdapter implements OrderCompletedPort {
    @Override
    public void orderCompleted(Order order) {
        System.out.println("OrderCompleted: " + order);
    }
}
