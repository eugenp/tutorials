package com.baeldung.hexagonal;

import com.baeldung.hexagonal.framework.OrderController;
import com.baeldung.hexagonal.domain.Order;

public class HexagonalArchitectureApplication {

    public static void main(String[] args) {
        OrderController orderController = new OrderController();
        Order order = orderController.createOrder("1234");
        System.out.println(order.getId());
    }
}
