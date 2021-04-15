package com.baeldung.architecture.hexagonal.shopcart.application.ports.in;

import java.util.UUID;

public interface CreateOrderUseCase {
	
	UUID createOrder(UUID productId, Integer quantity);
	
}
