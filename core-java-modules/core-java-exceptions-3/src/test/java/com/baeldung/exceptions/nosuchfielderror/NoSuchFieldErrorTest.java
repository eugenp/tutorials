package com.baeldung.exceptions.nosuchfielderror;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoSuchFieldErrorTest {

	@Test(expected = NoSuchFieldException.class)
	public void whenFieldNotFound_thenThrowNoSuchFieldException() throws Exception {
		NoSuchFieldError.getMessage();
	}

	@Test(expected = NoSuchFieldException.class)
	public void whenDependentFieldNotFound_thenThrowNoSuchFieldException() {
		NoSuchFieldError.getDependentMessage();
	}
	
	@Test
	public void whenDependentFieldNotFound_returnMessage() {
		String dependentMessage = NoSuchFieldError.getDependentMessage();
		assertTrue("Hello Baeldung".equals(dependentMessage));
	}
}
