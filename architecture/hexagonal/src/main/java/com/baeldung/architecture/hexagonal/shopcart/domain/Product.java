package com.baeldung.architecture.hexagonal.shopcart.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

	private UUID id;
	private String name;
	private BigDecimal price;
	
	public Product(String name, BigDecimal price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
}
