package com.baeldung.test.comparison;

import org.testng.annotations.Test;

public class TimeOutTest {
	@Test(timeOut = 1000,enabled=false)
	public void testInfinity() {
	    while (true);
	}
}
