package com.baeldung.architecture.hexagonal.shopcart.domain;

import java.util.UUID;

public class OrderItem {

	private Integer quantity;
	private UUID productId;
	
	public OrderItem(UUID productId, Integer quantity) {
		this.quantity = quantity;
		this.productId = productId;
	}
	
	public UUID getProductId() {
		return productId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
}
