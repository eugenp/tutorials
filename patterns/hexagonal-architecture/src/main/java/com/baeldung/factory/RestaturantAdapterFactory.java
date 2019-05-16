package com.baeldung.factory;

import com.baeldung.adapter.RestaurantSystemAdapter;
import com.baeldung.port.RestaurantSystemPort;

public class RestaturantAdapterFactory {

	public static RestaurantSystemPort getRestaturantSystem(String type) {
		return new RestaurantSystemAdapter();
	}
}
