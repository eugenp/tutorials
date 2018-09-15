package com.java.src;

import static org.junit.Assert.assertEquals;

public class RoundUpToHundredTest {
	public void roundupTest() {

		assertEquals("Rounded up to hundred", 100, 
				RoundUpToHundred.round(99));

		assertEquals("Rounded down to two hundred ", 200,
				RoundUpToHundred.round(200.2));

		assertEquals("Returns same rounded value", 300,
				RoundUpToHundred.round(300));

	}
}
