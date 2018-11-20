package com.hexa.example.factory;

import com.hexa.example.domain.FoodOrderDomainLogic;

public class FoodOrderFactory {

	public static FoodOrderDomainLogic getOrderProcessor() {
		return new FoodOrderDomainLogic();
	}
}
