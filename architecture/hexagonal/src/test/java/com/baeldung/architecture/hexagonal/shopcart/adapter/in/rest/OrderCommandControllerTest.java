package com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.architecture.hexagonal.shopcart.application.ports.in.CreateOrderUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = OrderCommandController.class)
public class OrderCommandControllerTest {
	
	@Autowired
	private MockMvc mocMvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CreateOrderUseCase createOrderUseCaseMock;
	
	@Test
	public void createOrder() throws JsonProcessingException, Exception {
		CreateOrderCommand createOrderCommand = new CreateOrderCommand();
		createOrderCommand.setProductId(UUID.randomUUID().toString());
		createOrderCommand.setQuantity(1);
		this.mocMvc.perform(put("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(createOrderCommand))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	
}
