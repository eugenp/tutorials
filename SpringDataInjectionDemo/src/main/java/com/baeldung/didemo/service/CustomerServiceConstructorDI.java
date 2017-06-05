package com.baeldung.didemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.didemo.model.Order;

@Service
public class CustomerServiceConstructorDI {

    OrderService orderService;

    @Autowired
    public CustomerServiceConstructorDI(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    public List<Order> getCustomerOrders(Long customerId) {
        return orderService.getOrdersForCustomer(customerId);
    }

}
