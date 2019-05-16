package com.baeldung.model;

public class Item {

	private String name;
	private Integer quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Item(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public Item() {
	}

}
