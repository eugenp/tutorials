package org.baeldung.java;

import org.junit.Test;

public class CoreJava8UnitTest {

	// tests -

	@Test
	public final void when_thenCorrect() {
		final Runnable r2 = () -> System.out.println("Hello world two!");
		r2.run();
	}

}
