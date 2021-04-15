package com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest;

public class CreateOrderCommand {

	private String productId;
	private Integer quantity;
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getProductId() {
		return productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
}
