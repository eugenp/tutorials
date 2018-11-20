package com.hexa.example.ports;

import java.util.List;

import com.hexa.example.domain.FoodOrderSummary;
import com.hexa.example.domain.FoodType;

public interface ReserveOrderPort {

	public FoodType createItem(String item, List<String> toppings);

	public FoodOrderSummary processOrder(List<FoodType> foodItems) throws Exception;
}
