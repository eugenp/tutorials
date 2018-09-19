package com.baeldung.memoryleaks.internedstrings;

import org.junit.Test;

public class StringInternMemoryLeakUnitTest {
	@Test
	public void givenJava6OrBelow_whenInterningLargeStrings_thenPermgenIncreases() {
		new InternedString().readString();
		System.out.print("Debug Point - VisuaLVM");
	}
	
	@Test
	public void givenJava6OrBelow_whenNotInterningLargeStrings_thenPermgenDoesntIncrease() {
		new StringObject().readString();
		System.out.print("Debug Point - VisuaLVM");
	}
}
