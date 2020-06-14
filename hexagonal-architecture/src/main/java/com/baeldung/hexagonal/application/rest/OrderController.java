package com.baeldung.hexagonal.application.rest;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> createOrder(@RequestBody Order order)  throws URISyntaxException {
        orderService.createOrder(order);
        URI uri = new URI("/customers/" + order.getId());
        return ResponseEntity.created(uri).body(order);
    }



    @GetMapping("/{id}")
    Order findOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }
}
