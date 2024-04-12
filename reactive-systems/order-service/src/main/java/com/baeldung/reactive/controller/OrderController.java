package com.baeldung.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.constants.OrderStatus;
import com.baeldung.domain.Order;
import com.baeldung.reactive.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Mono<Order> create(@RequestBody Order order) {
        log.info("Create order invoked with: {}", order);
        return orderService.createOrder(order)
            .flatMap(o -> {
                if (OrderStatus.FAILURE.equals(o.getOrderStatus())) {
                    return Mono.error(new RuntimeException("Order processing failed, please try again later. " + o.getResponseMessage()));
                } else {
                    return Mono.just(o);
                }
            });
    }

    @GetMapping
    public Flux<Order> getAll() {
        log.info("Get all orders invoked.");
        return orderService.getOrders();
    }

}