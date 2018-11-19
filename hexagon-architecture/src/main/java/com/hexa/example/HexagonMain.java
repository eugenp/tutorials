package com.hexa.example;

import java.util.ArrayList;
import java.util.Arrays;

import com.hexa.example.domain.FoodOrderSummary;
import com.hexa.example.domain.FoodType;
import com.hexa.example.factory.AdapterFactory;
import com.hexa.example.ports.ReserveOrderPort;

public class HexagonMain {

	public static void main(String[] args) {
		try {
			
			ReserveOrderPort orderProcessor=AdapterFactory.getOrderProcessor("MOBILE");
			ArrayList<FoodType> foodItems=new ArrayList<>();
			String[] toppings= {"MUSHROOM","CHEESE","HAM"};
			foodItems.add(orderProcessor.createItem("PIZZA", Arrays.asList(toppings)));
			
			String[] burger_toppings= {"CHICKEN","MOZARELLA"};
			foodItems.add(orderProcessor.createItem("BURGER", Arrays.asList(burger_toppings)));
			FoodOrderSummary summary=orderProcessor.processOrder(foodItems);
			System.out.println("**************************************************************");
			System.out.println("*                   "+summary.toString()+"                   *");
			System.out.println("**************************************************************");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
