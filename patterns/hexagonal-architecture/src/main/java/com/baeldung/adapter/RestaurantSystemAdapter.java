package com.baeldung.adapter;

import java.util.List;

import com.baeldung.domain.RestaurantSystem;
import com.baeldung.factory.RestaurantSystemFactory;
import com.baeldung.model.Item;
import com.baeldung.model.OrderSummary;
import com.baeldung.port.RestaurantSystemPort;

public class RestaurantSystemAdapter implements RestaurantSystemPort {

	RestaurantSystem restaurantSystem = RestaurantSystemFactory.getRestaurantSystem();

	@Override
	public OrderSummary processOrder(List<Item> items) {
		return restaurantSystem.processOrder(items);
	}

}
