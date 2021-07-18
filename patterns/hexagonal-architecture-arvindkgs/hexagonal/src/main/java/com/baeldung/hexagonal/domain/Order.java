package com.baeldung.hexagonal.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<Item> items;
	
	public Order() {
		items = new ArrayList<Item>();
	}

	public List<Item> getItems() {
		return items;
	}
}
