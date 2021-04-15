package com.baeldung.architecture.hexagonal.shopcart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

	private UUID id;
	private List<OrderItem> items = new ArrayList<>();
	
	public Order() {
		this.id = UUID.randomUUID();
	}
	
	public Order(UUID id, List<OrderItem> items) {
		this.id=id;
		this.items=items;
	}
	
	public void addProduct(UUID productId, Integer quntity) {
		this.items.add(new OrderItem(productId,quntity));
	}
	
	public UUID getId() {
		return id;
	}
	
	public List<OrderItem> getItems() {
		return items;
	}
	
}
