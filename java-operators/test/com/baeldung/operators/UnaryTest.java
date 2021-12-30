package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class UnaryTest {
	
	@Test
	public void plusAndMinusTest() {
		
		int number = 3;
		
		int numberChangedToNegative = - (number);
		assertEquals(numberChangedToNegative, -3);
		
		char charA = 'A';
		int intvalueofTheCharA = + charA;
		assertEquals(intvalueofTheCharA, 65);
	}
	

	@Test
	public void incrementAndDecrementTest() {
		
		int number = 3;
		number--;
		assertEquals(number, 2);
		
		number++;
		assertEquals(number, 3);
	}
	
	@Test
	public void incrementAndDecrementInTheMomentTest() {
		
		int number = 3;
		assertEquals(number--, 3);
		assertEquals(number, 2);
		
		assertEquals(--number, 1);
		assertEquals(number, 1);
		
	}
	
	@Test
	public void logicalComplementTest() {
		
		boolean allowed = false;
		
		assertEquals(allowed, false);
		assertEquals(!allowed, true);
		
		int number1 = 3;
		int number2 = 3;
		
		assertEquals(number1 == number2, true);
		assertEquals(!(number1 == number2), false);
		
	}

	
}
