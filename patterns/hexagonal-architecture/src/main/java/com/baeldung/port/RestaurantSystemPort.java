package com.baeldung.port;

import java.util.List;

import com.baeldung.model.Item;
import com.baeldung.model.OrderSummary;

public interface RestaurantSystemPort {

	public OrderSummary processOrder(List<Item> items);

}
