package com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.shopcart.application.ports.in.ListOrdersUseCase;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

	private final ListOrdersUseCase listOrdersUseCase;
	
	public OrderQueryController(ListOrdersUseCase listOrdersUseCase) {
		this.listOrdersUseCase=listOrdersUseCase;
	}
	
	@GetMapping
	public List<OrderModel> orders() {
		return this.listOrdersUseCase.listAllOrders();
	}
	
}
