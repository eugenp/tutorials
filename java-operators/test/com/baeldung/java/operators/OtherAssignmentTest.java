package com.baeldung.java.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class OtherAssignmentTest {

	
	@Test
	public void whenUseTheOperatorAssignmentIncrement() {
		int number1 = 10;
		number1 = number1 + 2;
		
		assertEquals(number1, 12);
		
		int number2 = 10;
		number2 += 2;
		
		assertEquals(number2, 12);
		
	}
	
	@Test
	public void whenUseTheOperatorAssignmentDecrement() {
		int number1 = 10;
		number1 = number1 - 2;
		
		assertEquals(number1, 8);
		
		int number2 = 10;
		number2 -= 2;
		
		assertEquals(number2, 8);
		
	}
	
	
	@Test
	public void whenUseTheOperatorAssignmentMultiplication() {
		int number1 = 10;
		number1 = number1 * 2;
		
		assertEquals(number1, 20);
		
		int number2 = 2;
		number2 *= 10;
		assertEquals(number2, 20);
		
	}
	
	@Test
	public void whenUseTheOperatorAssignmentDivision() {
		int number1 = 10;
		number1 = number1 / 2;
		
		assertEquals(number1, 5);
		
		int number2 = 10;
		number2 /= 2;
		assertEquals(number2, 5);
		
	}
	
	@Test
	public void whenUseTheOperatorAssignmentRemaider() {
		
		int number1 = 10;
		number1 = number1 % 2;
		
		assertEquals(number1, 0);
		
		int number2 = 10;
		number2 %= 2;
		
		assertEquals(number2, 0);
		
	}
}
