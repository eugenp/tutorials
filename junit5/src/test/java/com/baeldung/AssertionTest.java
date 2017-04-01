package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.junit.jupiter.api.Test;

public class AssertionTest {

	@Test
	public void testConvertToDoubleThrowException() {
		String age = "eighteen";
		expectThrows(NumberFormatException.class, () -> {
			convertToInt(age);
		});

		assertThrows(NumberFormatException.class, () -> {
			convertToInt(age);
		});
	}
	
	private static Integer convertToInt(String str) {
		if (str == null) {
			return null;
		}
		return Integer.valueOf(str);
	}

}
