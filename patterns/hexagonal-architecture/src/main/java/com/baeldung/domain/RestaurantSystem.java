package com.baeldung.domain;

import java.util.List;

import com.baeldung.model.Item;
import com.baeldung.model.OrderSummary;

public class RestaurantSystem {

	public OrderSummary processOrder(List<Item> items) {
		OrderSummary orderSummary = new OrderSummary();
		orderSummary.setOrderItems(items);
		orderSummary.setBillAmount(calculateBill(items));
		orderSummary.setOrderStatus("SUCCESS");
		return orderSummary;
	}

	private Double calculateBill(List<Item> items) {
		// actual code to calculate cost
		return 0.0;
	}

}
