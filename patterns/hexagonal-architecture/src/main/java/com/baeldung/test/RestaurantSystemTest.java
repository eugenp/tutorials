package com.baeldung.test;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.factory.RestaturantAdapterFactory;
import com.baeldung.model.Item;
import com.baeldung.model.OrderSummary;
import com.baeldung.port.RestaurantSystemPort;

public class RestaurantSystemTest {

	public static void main(String args[]) {

		// type is "MOBILE" here as the adapter is for mobile application
		RestaurantSystemPort restaurantSystem = RestaturantAdapterFactory.getRestaturantSystem("MOBILE");
		
		List<Item> items = new ArrayList<>();
		String itemName = "Margherita Pizza";
		Integer itemQuantity = 2;
		Item item = new Item(itemName, itemQuantity);
		items.add(item);
		
		OrderSummary orderSummary = restaurantSystem.processOrder(items);
		System.out.println(orderSummary.toString());

	}

}
