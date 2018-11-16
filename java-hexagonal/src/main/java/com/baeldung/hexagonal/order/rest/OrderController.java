package com.baeldung.hexagonal.order.rest;

import com.baeldung.hexagonal.order.Order;
import com.baeldung.hexagonal.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void saveOrder(@RequestBody Order order) {
        this.orderService.save(order);
    }

    @DeleteMapping
    public void deleteOrder(@RequestBody Order order) {
        this.orderService.delete(order);
    }

}
