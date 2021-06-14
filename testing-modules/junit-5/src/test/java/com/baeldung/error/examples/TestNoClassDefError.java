package com.baeldung.error.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

class TestNoClassDefError {

	@Test
	void when_run_then_assert_equals() {
		String str = "My Junits are working";
		assertEquals("My Junits are working",str);     
	}

}
