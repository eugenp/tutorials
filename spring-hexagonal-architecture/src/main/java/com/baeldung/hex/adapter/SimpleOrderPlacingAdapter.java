package com.baeldung.hex.adapter;

import com.baeldung.hex.core.Order;
import com.baeldung.hex.core.OrderManager;
import com.baeldung.hex.port.PlaceOrderPort;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleOrderPlacingAdapter implements PlaceOrderPort {
    OrderManager orderManager;

    public SimpleOrderPlacingAdapter(@Autowired OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Override
    public OrderManager getOrderManager() {
        return orderManager;
    }

    public void createSomeOrders() {
        for (int i = 0; i < 5; i++) {
            Order order = new Order("Item #" + i, "dummy@example.com");
            orderManager.placeOrder(order);
        }
    }
}
