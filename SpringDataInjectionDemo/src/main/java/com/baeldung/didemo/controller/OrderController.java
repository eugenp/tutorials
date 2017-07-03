package com.baeldung.didemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.didemo.model.Order;
import com.baeldung.didemo.service.CustomerServiceConstructorDI;
import com.baeldung.didemo.service.CustomerServiceConstructorWithoutSpringDI;
import com.baeldung.didemo.service.CustomerServiceFieldDI;
import com.baeldung.didemo.service.CustomerServiceSetterDI;
import com.baeldung.didemo.service.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    CustomerServiceConstructorDI constructorDI;

    @Autowired
    CustomerServiceFieldDI fieldDI;

    @Autowired
    CustomerServiceSetterDI setterDI;

    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getOrdersFieldDI(@RequestParam(required = false) String dIMethod) {
        if ("setter".equals(dIMethod)) {
            return setterDI.getCustomerOrders(1l);
        } else if ("constructor".equals(dIMethod)) {
            return constructorDI.getCustomerOrders(1l);
        } else if ("field".equals(dIMethod)) {
            return fieldDI.getCustomerOrders(1l);
        } else {
            OrderService orderSvc = new OrderService();
            CustomerServiceConstructorWithoutSpringDI customerSvc = new CustomerServiceConstructorWithoutSpringDI(orderSvc);
            return customerSvc.getCustomerOrders(1l);
        }
    }
}
