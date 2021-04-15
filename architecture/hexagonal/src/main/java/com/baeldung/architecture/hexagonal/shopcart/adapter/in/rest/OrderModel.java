package com.baeldung.architecture.hexagonal.shopcart.adapter.in.rest;

public class OrderModel {

	private String id;

	public OrderModel(String id) {
		this.id=id;
	}
	
	public String getId() {
		return id;
	}
	
}
