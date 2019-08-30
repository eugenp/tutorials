package com.baeldung.hex.core;

import com.baeldung.hex.port.OrderCompletedPort;

import java.util.Set;

public interface OrderManager {
    Set<OrderCompletedPort> getOrderCompletedPortSet();
    void placeOrder(Order order);
}
