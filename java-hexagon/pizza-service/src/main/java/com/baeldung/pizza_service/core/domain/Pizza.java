package com.baeldung.pizza_service.core.domain;

import java.util.Arrays;

public class Pizza {
	private String name;
	private int price;
	private String[] toppings;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String[] getToppings() {
		return toppings;
	}
	public void setToppings(String[] toppings) {
		this.toppings = toppings;
	}
	@Override
	public String toString() {
		return "Pizza [name=" + name + ", price=" + price + ", toppings=" + Arrays.toString(toppings) + "]";
	}
	
}
