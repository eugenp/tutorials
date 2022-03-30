package com.baeldung.graphqlvsrest.controller;

import com.baeldung.graphqlvsrest.entity.Order;
import com.baeldung.graphqlvsrest.entity.Product;
import com.baeldung.graphqlvsrest.model.ProductModel;
import com.baeldung.graphqlvsrest.repository.OrderRepository;
import com.baeldung.graphqlvsrest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
