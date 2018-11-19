package com.hexa.example.domain;

import java.io.Serializable;
import java.util.List;

public class FoodOrderDomain implements Serializable{

	
	
	private List<FoodType> food;
	
	public FoodOrderSummary processOrder(List<FoodType> foods) {
		FoodOrderSummary summary=new FoodOrderSummary();
		summary.setBillAmount("$23.12");
		summary.setOrderStatus("Success");
		return summary;
	}
	
}
