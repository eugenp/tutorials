package com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.shopcart.application.ports.in.CreateOrderUseCase;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

	private final CreateOrderUseCase createOrderUseCase;
		
	public OrderCommandController(CreateOrderUseCase createOrderUseCase) {
		this.createOrderUseCase = createOrderUseCase;
	}

	@PutMapping
	public void create(@RequestBody CreateOrderCommand createOrderRequest) {
		this.createOrderUseCase.createOrder(UUID.fromString(createOrderRequest.getProductId()), createOrderRequest.getQuantity());
	}
	
}
