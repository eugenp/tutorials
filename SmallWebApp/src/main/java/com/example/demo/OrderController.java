package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/order") 
public class OrderController{ 
	@Autowired private OrderService orderService; 
	
	@PostMapping 
	public void createOrder(@RequestBody Order order) { 
		orderService.createOrder(order); 
	} 
	
	@GetMapping("/{orderNumber}") 
	public Order getOrder(@PathVariable Long orderNumber) { 
		return orderService.getOrder(orderNumber);		
	} 
	
	@GetMapping 
	public List<Order> allOrders() { 
		return orderService.allOrders(); 
	} 
}
