package com.baeldung.hex.core;

import com.baeldung.hex.port.OrderCompletedPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class OrderManagerImpl implements OrderManager {
    Set<OrderCompletedPort> orderCompletedPortSet;

    @Autowired
    public OrderManagerImpl(Set<OrderCompletedPort> orderCompletedPorts) {
        this.orderCompletedPortSet = orderCompletedPorts;
    }

    @Override
    public Set<OrderCompletedPort> getOrderCompletedPortSet() {
        return orderCompletedPortSet;
    }

    @Override
    public void placeOrder(Order order) {
        completeOrderLater(order);
        System.out.println("Order received and in progress: " + order);
    }

    public void completeOrderLater(Order order) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (OrderCompletedPort orderCompletedPort : getOrderCompletedPortSet()) {
                order.complete();
                orderCompletedPort.orderCompleted(order);
            }
        }).start();
    }
}
