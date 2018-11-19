package com.hexa.example.factory;

import com.hexa.example.domain.FoodOrderDomain;

public class FoodOrderFactory {

	public static FoodOrderDomain getOrderProcessor() {
		return new FoodOrderDomain();
	}
}
