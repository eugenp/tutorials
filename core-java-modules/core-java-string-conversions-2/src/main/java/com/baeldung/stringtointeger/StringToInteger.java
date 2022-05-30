package com.baeldung.stringconversions;

public class StringToInteger {
	public static void main(String[] args) {
		
		int number1 = Integer.parseInt("11"); // 11
		int number2 = Integer.parseInt("+11"); // 11
		int number3 = Integer.parseInt("-11"); // -11
		
		int number4 = Integer.parseInt("11", 16); // 17
		int number5 = Integer.parseInt("A", 16); // 10
		int number6 = Integer.parseInt("7", 8); // 7
		
		int number7 = Integer.parseInt("ABCDEFG", 1, 4, 36); // 14701
		int number8 = Integer.parseInt("abcdefg", 1, 6, 36); // 19053015
		
		Integer number9 = Integer.valueOf("11"); // 11
		Integer number10 = Integer.valueOf("+11"); // 11
		Integer number11 = Integer.valueOf("-11"); // -11
		 
		Integer number12 = Integer.valueOf(11); // 11
		Integer number13 = Integer.valueOf(+11); // 11
		Integer number14 = Integer.valueOf(-11); // -11
		Integer number15 = Integer.valueOf('A'); // 65
		
		Integer number16 = Integer.valueOf("11", 16); // 17
		Integer number17 = Integer.valueOf("A", 16); // 10
		Integer number18 = Integer.parseInt("7", 8); //7
		
		System.out.println("number1 :: "+number1);
		System.out.println("number2 :: "+number2);
		System.out.println("number3 :: "+number3);
		System.out.println("number4 :: "+number4);
		System.out.println("number5 :: "+number5);
		System.out.println("number6 :: "+number6);
		System.out.println("number7 :: "+number7);
		System.out.println("number8 :: "+number8);
		System.out.println("number9 :: "+number9);
		System.out.println("number10 :: "+number10);
		System.out.println("number11 :: "+number11);
		System.out.println("number12 :: "+number12);
		System.out.println("number13 :: "+number13);
		System.out.println("number14 :: "+number14);
		System.out.println("number15 :: "+number15);
		System.out.println("number16 :: "+number16);
		System.out.println("number17 :: "+number17);
		System.out.println("number18 :: "+number18);
        
	}
}
