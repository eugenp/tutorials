package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.domain.Order;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private List<Order> orders = new ArrayList<Order>();

    public Order createOrder(String sku) {
        Order order = new Order(sku);
        orders.add(order);
        return order;
    }
}