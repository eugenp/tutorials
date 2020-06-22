package com.baeldung.architecture.hexagonal.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.core.Order;
import com.baeldung.architecture.hexagonal.ports.OrderService;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

}
