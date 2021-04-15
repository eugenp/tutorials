package com.baeldung.architecture.hexagonal.shopcart.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.architecture.hexagonal.shopcart.application.ports.out.SaveOrderPort;

public class CreateOrderServiceTest {

	private SaveOrderPort saveOrderPortMock = Mockito.mock(SaveOrderPort.class);	
	private CreateOrderService createOrderService = new CreateOrderService(saveOrderPortMock);
	
	@Test
	public void createNewOrderTest() {
		UUID orderId = createOrderService.createOrder(UUID.randomUUID(), 1);		
		assertThat(orderId).isNotNull();
	}
	
}
