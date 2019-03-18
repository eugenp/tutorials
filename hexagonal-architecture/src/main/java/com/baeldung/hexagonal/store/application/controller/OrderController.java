package com.baeldung.hexagonal.store.application.controller;

import com.baeldung.hexagonal.store.application.dto.request.OrderCreateRequestDto;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Order> createOrder(@PathVariable("customerId") int customerId, @RequestBody OrderCreateRequestDto requestDto) {
        Optional<Order> order = this.orderService.processNewCustomerOrder(customerId, requestDto.getProductQuantityMap());
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }

}
