package com.baeldung.didemo.service;

import java.util.List;

import com.baeldung.didemo.model.Order;

public class CustomerServiceConstructorWithoutSpringDI {

    OrderService orderService;

    public CustomerServiceConstructorWithoutSpringDI(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    public List<Order> getCustomerOrders(Long customerId) {
        return orderService.getOrdersForCustomer(customerId);
    }

}
