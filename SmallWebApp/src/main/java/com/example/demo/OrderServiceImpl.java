package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


public class OrderServiceImpl implements OrderService { 

	@Autowired 
	private OrderRepository orderRepository; 

	@Override 
	public void createOrder(Order order) { 
		orderRepository.createOrder(order); 
	} 
	
	@Override 
	public Order getOrder(Long orderNumber) { 
		return orderRepository.getOrder(orderNumber); 
	} 
	
	@Override 
	public List<Order> allOrders() { 
		return orderRepository.allOrders(); 
	} 
}
