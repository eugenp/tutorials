package br.com.baeldung.operators;

public class Arithmetic {

	public static void useAdditiveToConcatenation() {
		String salutation = "Hello";
		String person = "John";
		String exclamationMark="!";
		String space = " ";
		
		System.out.println("Sentence: " + salutation + space + person + space + exclamationMark);
	}
	
	public static void useAdditiveToArihmeticOperation() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 + number2);
	}
	
	public static void subtract() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 - number2);
	}
	
	public static void multiply() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 * number2);
	}
	
	public static void divide() {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 / number2);
	}
	
	public static void calculateTheRemainder () {
		int number1 = 10;
		int number2 = 5;
		
		System.out.println(number1 % number2);
		
		int number3 = 7;
		
		System.out.println(number1 % number3);
	}
}
