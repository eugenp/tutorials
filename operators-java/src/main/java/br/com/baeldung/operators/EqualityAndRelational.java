package br.com.baeldung.operators;

public class EqualityAndRelational {
	
	public static void testEqualTo() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 == number2);
		
		//testing decision
		if(number1 == number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}
	
	public static void testNotEqualTo() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 != number2);
		
		//testing decision
		if(number1 != number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}
	
	public static void testGreaterThan() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 > number2);
		
		//testing decision
		if(number1 > number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}
	
	public static void testGreaterThanOrEqualTo() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 >= number2);
		
		//testing decision
		if(number1 >= number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}
	
	public static void testLessThan() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 < number2);
		
		//testing decision
		if(number1 < number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}
	
	public static void testLessThanOrEqualTo() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 <= number2);
		
		//testing decision
		if(number1 <= number2) {
			System.out.println("make something...");
		}else {
			System.out.println("make other something");
		}
	}

}
