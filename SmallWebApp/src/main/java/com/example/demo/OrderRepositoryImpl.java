package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository 
public class OrderRepositoryImpl implements OrderRepository { 
	
	private Map<Long, Order> orderDB = new HashMap<Long, Order>(); 
	
	@Override 
	public void createOrder(Order order) { 
		orderDB.put(order.getOrderNumber(), order); 
	} 
	
	@Override 
	public Order getOrder(Long orderNumber) { 
		return orderDB.get(orderNumber); 
	} 
	
	@Override 
	public List<Order> allOrders() { 
		return orderDB.values().stream().collect(Collectors.toList()); 
	} 
}
