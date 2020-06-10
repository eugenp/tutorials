package com.baeldung.hexagonal.controllers;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.service.DomainOrderService;
import com.baeldung.hexagonal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process")
    @ResponseBody Order processOrder(@RequestBody Order request) {
        Order order = orderService.applyVAT(request);
        return order;
    }
}