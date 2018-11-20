package com.hexa.example.adapters;

import java.util.List;

import com.hexa.example.domain.FoodOrderDomainLogic;
import com.hexa.example.domain.FoodOrderSummary;
import com.hexa.example.domain.FoodType;
import com.hexa.example.factory.FoodOrderFactory;
import com.hexa.example.ports.ReserveOrderPort;

public class ReserveOrderAdapter implements ReserveOrderPort {

	FoodOrderDomainLogic orderProcessor = FoodOrderFactory.getOrderProcessor();

	public FoodType createItem(String item, List<String> toppings) {
		FoodType foodType = new FoodType();
		try {
			foodType.setItem(item);
			foodType.setAdditionalToppings(toppings);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return foodType;
	}

	public FoodOrderSummary processOrder(List<FoodType> foodItems) throws Exception {
		if (foodItems.size() <= 0) {
			throw new Exception("No food items to process");
		}
		return orderProcessor.processOrder(foodItems);
	}

}
