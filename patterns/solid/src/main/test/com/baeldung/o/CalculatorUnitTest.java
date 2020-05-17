package com.baeldung.o;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorUnitTest {

	private static final double NUMBER_1 = 10.0;
	private static final double NUMBER_2 = 20.0;
	private static final double SUM = 30.0;
	private static final double SUBTRACTION_RESULT = 10.0;
	private static final double DIVISION_RESULT = 2.0;
	
	private Calculator calculator;
	
	@BeforeEach
	public void setUp() {
		 calculator = new Calculator();
	}

	@Test
	public void whenAddTwoNumber_returnSum() {
		Addition addition = new Addition(NUMBER_1, NUMBER_2);
		calculator.calculate(addition);
		assertEquals(SUM, addition.getResult(), 0.0);
	}

	@Test
	public void whenSutractTwoNumber_returnCorrectResult() {
		Subtraction subtraction = new Subtraction(NUMBER_2, NUMBER_1);
		calculator.calculate(subtraction);
		assertEquals(SUBTRACTION_RESULT, subtraction.getResult(), 0.0);
	}

	@Test
	public void whenDivideTwoNumber_returnCorrectResult() {
		Division division = new Division(NUMBER_2, NUMBER_1);
		calculator.calculate(division);
		assertEquals(DIVISION_RESULT, division.getResult(), 0.0);
	}

}
