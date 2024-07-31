package com.baeldung.multiplecachemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.multiplecachemanager.bo.CustomerDetailBO;
import com.baeldung.multiplecachemanager.bo.OrderDetailBO;
import com.baeldung.multiplecachemanager.entity.Customer;
import com.baeldung.multiplecachemanager.entity.Order;

@RestController
public class MultipleCacheManagerController {

    @Autowired
    private CustomerDetailBO customerDetailBO;

    @Autowired
    private OrderDetailBO orderDetailBO;

    @GetMapping(value = "/getCustomer/{customerid}")
    public Customer getCustomer(@PathVariable Integer customerid) {
        return customerDetailBO.getCustomerDetail(customerid);
    }

    @GetMapping(value = "/getCustomerOrders/{customerid}")
    public List<Order> getCustomerOrders(@PathVariable Integer customerid) {
        return customerDetailBO.getCustomerOrders(customerid);
    }

    @GetMapping(value = "/getOrder/{orderid}")
    public Order getOrder(@PathVariable Integer orderid) {
        return orderDetailBO.getOrderDetail(orderid);
    }

    @GetMapping(value = "/getOrderPrice/{orderid}")
    public double getOrderPrice(@PathVariable Integer orderid) {
        return orderDetailBO.getOrderPrice(orderid);
    }
}
