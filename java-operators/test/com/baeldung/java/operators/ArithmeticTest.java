package com.baeldung.java.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ArithmeticTest {

	
	@Test
	public void whenUseTheOperatorArithmeticAddition() {
		
		int resultSumPositive = 10 + 5;		
		
		assertEquals(resultSumPositive, 15);

		
		int resultSumNegative = -10 + (-5);		
		
		assertEquals(resultSumNegative, -15);

		
		int resultSumNegativeAndPositive = -10 + 5;		
		
		assertEquals(resultSumNegativeAndPositive, -5);

		
		int resultSuPositiveAndNegativ = 10 + (-5);		
		
		assertEquals(resultSuPositiveAndNegativ, 5);

		
		String sentence = "Hello world!";
		String wordsConcatenated = "Hello " + "world" + "!";
		
		assertEquals(sentence, wordsConcatenated);
	
	}
	
	@Test
	public void whenUseTheOperatorSubtraction() {
		
		int resultSubtractionPositive = 10 - 5;		
		
		assertEquals(resultSubtractionPositive, 5);

		
		int resultSubtractionNegative = -10 - (-5);		
		
		assertEquals(resultSubtractionNegative, -5);

		
		int resultSubtractionNegativeAndPositive = -10 - (+5);		
		
		assertEquals(resultSubtractionNegativeAndPositive, -15);

		
		int resultSubtractionPositiveAndNegative = 10 - (-5);		
		
		assertEquals(resultSubtractionPositiveAndNegative, 15);


	}
	
	@Test
	public void whenUseTheOperatorMultiplication() {
		
		int resultMultiplicationPositive = 10 * 5;		
		
		assertEquals(resultMultiplicationPositive, 50);

		
		int resultMultiplicationNegative = -10 * (-5);		
		
		assertEquals(resultMultiplicationNegative, 50);

		
		int resultMultiplicationNegativeAndPositive = -10 * (+5);		
		
		assertEquals(resultMultiplicationNegativeAndPositive, -50);

		
		int resultMultiplicationPositiveAndNegative = 10 * (-5);		
		
		assertEquals(resultMultiplicationPositiveAndNegative, -50);


	}
	
	@Test
	public void whenUseTheOperatorDivision() {
		
		int resultDivisionPositive = 10 / 5;		
		
		assertEquals(resultDivisionPositive , 2);

		
		int resultDivisionPositiveNegative = -10 / (-5);		
		
		assertEquals(resultDivisionPositiveNegative, 2);

		
		int resultDivisionNegativeAndPositive = -10 / (+5);		
		
		assertEquals(resultDivisionNegativeAndPositive, -2);

		
		int resultDivisionPositiveAndNegative = 10 / (-5);		
		
		assertEquals(resultDivisionPositiveAndNegative, -2);


	}
	
	@Test
	public void whenUseTheOperatorRemainder() {
		
		int resultDivisionPositive = 10 % 5;		
		
		assertEquals(resultDivisionPositive , 0);

	}
	
}
