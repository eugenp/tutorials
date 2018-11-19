package com.hexa.example;

import java.util.Scanner;

public class HexagonMain {

	public static void main(String[] args) {
		try {
			Scanner scanner=new Scanner(System.in);
			System.out.println("Enter The Item: ");
			String item=scanner.nextLine();
			System.out.println("This is item "+item);
			/*ReserveOrderPort orderProcessor=AdapterFactory.getOrderProcessor("MOBILE");
			ArrayList<FoodType> foodItems=new ArrayList<>();
			String[] toppings= {"MUSHROOM","CHEESE","HAM"};
			foodItems.add(orderProcessor.createItem("PIZZA", Arrays.asList(toppings)));
			
			String[] burger_toppings= {"CHICKEN","MOZARELLA"};
			foodItems.add(orderProcessor.createItem("BURGER", Arrays.asList(burger_toppings)));
			FoodOrderSummary summary=orderProcessor.processOrder(foodItems);
			System.out.println("**************************************************************");
			System.out.println("*                   "+summary.toString()+"                   *");
			System.out.println("**************************************************************");*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
