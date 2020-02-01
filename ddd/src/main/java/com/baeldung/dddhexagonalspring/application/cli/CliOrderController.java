package com.baeldung.dddhexagonalspring.application.cli;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalspring.domain.Product;
import com.baeldung.dddhexagonalspring.domain.service.OrderService;

@Component
public class CliOrderController{

    private final OrderService orderService;

    @Autowired
    public CliOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public UUID createOrder(Product product) {
        return orderService.createOrder(product);
    }

    public void addProduct(UUID orderId, Product product) {
        orderService.addProduct(orderId, product);
    }

    public void deleteProduct(UUID orderId, UUID productId) {
        orderService.deleteProduct(orderId, productId);
    }

    public void completeOrder(UUID orderId) {
        orderService.completeOrder(orderId);
    }

}
