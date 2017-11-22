package com.baeldung.array;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ArrayInverterTest {

	private String[] fruits = {"apples", "tomatoes", "bananas", "guavas", "pineapples", "oranges"};

	@Test
	public void invertArrayWithForLoop() {
		ArrayInverter inverter = new ArrayInverter();
		inverter.invertUsingFor(fruits);

		assertArrayEquals(new String[] {"oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples"}, fruits);
	}

	@Test
	public void invertArrayWithCollectionsReverse() {
		ArrayInverter inverter = new ArrayInverter();
		inverter.invertUsingCollectionsReverse(fruits);

		assertArrayEquals(new String[] {"oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples"}, fruits);
	}

	@Test
	public void invertArrayWithStreams() {
		ArrayInverter inverter = new ArrayInverter();
		assertArrayEquals(
			new String[] {"oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples"},
			inverter.invertUsingStreams(fruits));
	}

}
