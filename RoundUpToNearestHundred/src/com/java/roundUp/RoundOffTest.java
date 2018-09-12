package com.java.roundUp;


import static org.junit.Assert.*;
import org.junit.Test;

public class RoundOffTest {

	@Test
	public void testGenerateRoundNo() {

		double input1 = 22.5;
		int output1 = 100;

		double input2 = 100.5;
		int output2 = 200;

		RoundOff off = new RoundOff();
		int roundedResult1 = off.GenerateRoundNo(input1);
		assertTrue(roundedResult1 == output1);

		int roundedResult2 = off.GenerateRoundNo(input2);
		assertTrue(roundedResult2 == output2);
	}
}
