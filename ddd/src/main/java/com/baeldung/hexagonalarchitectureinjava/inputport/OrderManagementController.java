package com.baeldung.hexagonalarchitectureinjava.inputport;

import com.baeldung.hexagonalarchitectureinjava.domain.OrderManagementService;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.ConfimrOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.CreateOrderRequest;
import com.baeldung.hexagonalarchitectureinjava.domain.beans.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderManagementController {

    private final OrderManagementService orderService;

    @Autowired
    public OrderManagementController(OrderManagementService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create")
    OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping(value = "/{id}/confirm")
    OrderResponse confirmOrder(@PathVariable UUID orderId, @RequestBody ConfimrOrderRequest request) {
        return orderService.confirmOrder(orderId, request);
    }
    @DeleteMapping(value = "/{id}/cancel")
    OrderResponse removeOrder(@PathVariable UUID orderId) {
        return orderService.cancelOrder(orderId);
    }
}
