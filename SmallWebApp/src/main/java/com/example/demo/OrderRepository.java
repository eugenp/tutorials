package com.example.demo;

import java.util.List;

public interface OrderRepository { 
    void createOrder(Order order); 
    Order getOrder(Long orderNumber); 
    List<Order> allOrders();
}
