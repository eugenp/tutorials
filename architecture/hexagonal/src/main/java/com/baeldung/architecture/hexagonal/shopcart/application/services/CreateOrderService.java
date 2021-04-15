package com.baeldung.architecture.hexagonal.shopcart.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baeldung.architecture.hexagonal.shopcart.application.ports.in.CreateOrderUseCase;
import com.baeldung.architecture.hexagonal.shopcart.application.ports.out.SaveOrderPort;
import com.baeldung.architecture.hexagonal.shopcart.domain.Order;

@Service
public class CreateOrderService implements CreateOrderUseCase{

	private final SaveOrderPort saveOrderPort;
	
	public CreateOrderService(SaveOrderPort saveOrderPort) {
		this.saveOrderPort=saveOrderPort;
	}
	
	@Override
	public UUID createOrder(UUID productId, Integer quantity) {
		Order order = new Order();
		order.addProduct(productId, quantity);
		this.saveOrderPort.save(order);
		return order.getId();
	}

}
