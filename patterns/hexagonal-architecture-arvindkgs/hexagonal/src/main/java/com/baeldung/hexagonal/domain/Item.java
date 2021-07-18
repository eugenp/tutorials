package com.baeldung.hexagonal.domain;

public class Item {
	private int amount;
	private int quantity;

	public Item(int amount, int quantity) {
		this.amount = amount;
		this.quantity = quantity;
	}

	public int getAmount() {
		return amount;
	}

	public int getQuantity() {
		return quantity;
	}
}
