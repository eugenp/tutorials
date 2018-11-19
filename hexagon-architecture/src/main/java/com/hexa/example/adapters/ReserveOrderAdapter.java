package com.hexa.example.adapters;

import java.util.List;

import com.hexa.example.domain.FoodOrderDomain;
import com.hexa.example.domain.FoodOrderSummary;
import com.hexa.example.domain.FoodType;
import com.hexa.example.factory.FoodOrderFactory;
import com.hexa.example.ports.ReserveOrderPort;

public class ReserveOrderAdapter implements ReserveOrderPort{

	FoodOrderDomain orderProcessor=FoodOrderFactory.getOrderProcessor();
	
	public FoodType createItem(String item, List<String> toppings) {
		// TODO Auto-generated method stub
		FoodType foodType=new FoodType();
		try {
			foodType.setItem(item);
			foodType.setAdditionalToppings(toppings);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return foodType;
	}

	public FoodOrderSummary processOrder(List<FoodType> foodItems) throws Exception {
		// TODO Auto-generated method stub
		if(foodItems.size() <=0) {
			throw new Exception("No food items to process");
		}
		return orderProcessor.processOrder(foodItems);
	}

	
}
