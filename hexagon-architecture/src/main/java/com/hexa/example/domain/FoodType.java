package com.hexa.example.domain;

import java.util.ArrayList;
import java.util.List;

public class FoodType {

	private String item;
	
	private List<String> additionalToppings;
	

	public String getItem() {
		return item;
	}

	public void setItem(String item) throws Exception {
		if(Food.valueOf(item) != null) {
		this.item = item;
		}else {
			throw new Exception("ITEM NOT VALID EXCEPTION");
		}
	}

	public List<String> getAdditionalToppings() {
		return additionalToppings;
	}

	public void setAdditionalToppings(List<String> additionalToppings) throws Exception {
		
		for (String topping : additionalToppings) {
			if(Toppings.valueOf(topping) == null) {
				throw new Exception("Invalid Topping List");
			}
		}
		this.additionalToppings = additionalToppings;
	}
	
	
}
