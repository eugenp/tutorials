package com.baeldung.design.hex.adapter.driven.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baeldung.design.hex.business.domain.Item;

@Component
public class DataSource {
	private static List<Item> allItems = new ArrayList<>();

	public String placeOrder(List<Item> items) {
		allItems.addAll(items);
		return "1";
	}

	public List<Item> getOrderedItems(String orderId) {
		return allItems;
	}
}
