package com.baeldung.hexagonal.outside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.boundary.input.OrderService;
import com.baeldung.hexagonal.core.entities.Order;

@RestController public class OrderController {

	@Autowired OrderService orderService;
	
	@GetMapping(path = "/orders/list")
	public Iterable<Order> getOrders(){
		return orderService.getOrders();
	}
	
	@GetMapping(path = "/orders/add")
	public String placeOrder(@RequestParam Double total) {
		boolean isPlaced = orderService.registerOrder(orderService.createOrder(total));	
		return isPlaced ? "Ok" : "Nok";
	}
	
	@GetMapping(path = "/orders/add-commission")
	public String placeCommissionOrder(@RequestParam Double total) {
		boolean isPlaced = orderService.registerOrder(orderService.createOrder(total*1.05));		
		return isPlaced ? "Ok" : "Nok";
	}

}
