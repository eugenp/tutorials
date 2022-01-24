package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface OrderService { 
	void createOrder(Order order); 
	Order getOrder(Long orderNumber); 
	List<Order> allOrders(); 
}
