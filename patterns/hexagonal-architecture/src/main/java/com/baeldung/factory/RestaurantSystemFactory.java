package com.baeldung.factory;

import com.baeldung.domain.RestaurantSystem;

public class RestaurantSystemFactory {

	public static RestaurantSystem getRestaurantSystem() {
		return new RestaurantSystem();
	}

}
