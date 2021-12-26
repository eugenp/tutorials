package br.com.baeldung.operators;

public class Unary {
	
	public static void changePositiveToNegative() {
		int number1 = 10;
		int number2 = 5;
		int positive = number2 + number1;
		System.out.println("Positive value");
		System.out.println(positive);
		System.out.println("Positive value changed to negative value");
		System.out.println(- positive);
	}
	
	public static void showPositive() {
		int number = + 10;
		System.out.println(number);

	}
	
	public static void increment_1() {
		int number = 10;
		number++;
		System.out.println(number);

	}
	
	public static void decrement_1() {
		int number = 10;
		number--;
		System.out.println(number);

	}
	
	public static void changeBooleanValue() {
		boolean forbidden = true;
		System.out.println(!forbidden);

	}


}
