package com.hexa.example.domain;

import java.io.Serializable;
import java.util.List;

public class FoodOrderDomainLogic implements Serializable {

	public FoodOrderSummary processOrder(List<FoodType> foods) {
		FoodOrderSummary summary = new FoodOrderSummary();
		try {
			summary.setBillAmount(calculateBill(foods));
			summary.setOrderStatus("Success");
		} catch (Exception e) {
			summary.setOrderStatus("Failed");
			e.printStackTrace();
		}
		return summary;
	}

	private String calculateBill(List<FoodType> foods) throws Exception {
		double totalBill = 0.00;
		for (FoodType food : foods) {
			if (food.getItem().equalsIgnoreCase(Food.PIZZA.name())) {
				totalBill = totalBill + 12.99;
			} else if (food.getItem().equalsIgnoreCase(Food.BURGER.name())) {
				totalBill = totalBill + 9.99;
			} else if (food.getItem().equalsIgnoreCase(Food.PINANI.name())) {
				totalBill = totalBill + 11.99;
			} else if (food.getItem().equalsIgnoreCase(Food.BREAD.name())) {
				totalBill = totalBill + 6.99;
			} else {
				throw new Exception("Invalid Food Item");
			}
			totalBill = totalBill + (food.getAdditionalToppings().size() * 0.5);
		}
		return "$" + totalBill;

	}

}
