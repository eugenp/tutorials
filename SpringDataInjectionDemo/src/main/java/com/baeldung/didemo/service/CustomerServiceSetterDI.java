package com.baeldung.didemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.didemo.model.Order;

@Service
public class CustomerServiceSetterDI {

    OrderService orderService;

    public List<Order> getCustomerOrders(Long customerId) {
        return orderService.getOrdersForCustomer(customerId);
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
