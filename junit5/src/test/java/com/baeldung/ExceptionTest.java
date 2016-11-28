package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.junit.jupiter.api.Test;

public class ExceptionTest {

	@Test
	void shouldThrowException() {
		Throwable exception = expectThrows(UnsupportedOperationException.class, () -> {
			throw new UnsupportedOperationException("Not supported");
		});
		assertEquals(exception.getMessage(), "Not supported");
	}

	@Test
	void assertThrowsException() {
		String str = null;
		assertThrows(IllegalArgumentException.class, () -> {
			Integer.valueOf(str);
		});
	}
}
