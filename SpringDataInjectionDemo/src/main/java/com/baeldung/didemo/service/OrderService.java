package com.baeldung.didemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.didemo.model.Order;

@Service
public class OrderService {

    public List<Order> getOrdersForCustomer(Long id) {
        List<Order> orders = new ArrayList<Order>();
        
        Order order1 = new Order();
        order1.setId(1);
        order1.setItem("Pizza");
        
        Order order2 = new Order();
        order2.setId(1);
        order2.setItem("Garlic Bread");
        
        orders.add(order1);
        orders.add(order2);
        
        return orders;
    }
    
}
