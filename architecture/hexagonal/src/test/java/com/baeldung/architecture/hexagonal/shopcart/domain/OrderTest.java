package com.baeldung.architecture.hexagonal.shopcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class OrderTest {

	@Test
	void addProductToOrder() {
		Order order = new Order();
		order.addProduct(UUID.randomUUID(), 1);
		assertThat(order.getItems()).size().isEqualTo(1);
		assertThat(order.getId()).isNotNull();
	}
	
}
