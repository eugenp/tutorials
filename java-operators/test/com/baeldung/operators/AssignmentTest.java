package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class AssignmentTest {

	
	@Test
	public void assignmentsTest() {
		int number1 = 10;
		number1 = number1 + 2;
		
		int number2 = 10;
		number2 +=  2;
		
		assertEquals(number1, 12);
		assertEquals(number2, 12);
		
		number1 = 10;
		number1 = number1 - 2;
		assertEquals(number1, 8);
		
		number2 = 10;
		number2 -= 2;
		assertEquals(number1, 8);
		
		
		number1 = 10;
		number1 = number1 * 2;
		assertEquals(number1, 20);
		
		number2 = 2;
		number2 *= 10;
		assertEquals(number1, 20);
		
		number1 = 10;
		number1 = number1 / 2;
		assertEquals(number1, 5);
		
		number2 = 2;
		number2 /= 5;
		assertEquals(number1, 5);
		
		
		number1 = 10;
		number1 = number1 % 2;
		assertEquals(number1, 0);
		
		number2 = 10;
		number2 %= 2;
		assertEquals(number1, 0);
		
		
	}
	
}
