package com.baeldung.graphqlvsrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.repository.OrderRepository;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping()
    public List<Order> getOrders(@RequestParam("product-id") Integer productId){
        return orderRepository.getOrdersByProduct(productId);
    }
}
