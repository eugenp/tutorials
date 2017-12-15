package com.baeldung.junit5.bean.test;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.junit5.bean.NumbersBean;

/**
 * Test class for {@link NumbersBean}.
 * 
 * @author Donato Rimenti
 *
 */
public class NumbersBeanUnitTest {

	/**
	 * The bean to test.
	 */
	private NumbersBean bean = new NumbersBean();

	/**
	 * Tests that when an even number is passed to
	 * {@link NumbersBean#isNumberEven(int)}, true is returned.
	 */
	@Test
	void givenEvenNumber_whenCheckingIsNumberEven_thenTrue() {
		boolean result = bean.isNumberEven(8);

		Assertions.assertTrue(result);
	}

	/**
	 * Tests that when an odd number is passed to
	 * {@link NumbersBean#isNumberEven(int)}, false is returned.
	 */
	@Test
	void givenOddNumber_whenCheckingIsNumberEven_thenFalse() {
		boolean result = bean.isNumberEven(3);

		Assertions.assertFalse(result);
	}

	/**
	 * Tests that when a number lower than 10 is passed to
	 * {@link NumbersBean#isNumberEven(int)}, the result is returned in less
	 * than 10 milliseconds.
	 */
	@Test
	void givenLowerThanTenNumber_whenCheckingIsNumberEven_thenResultUnderTenMillis() {
		Assertions.assertTimeout(Duration.ofMillis(10), () -> bean.isNumberEven(3));
	}

	/**
	 * Tests that when <code>null</code> is passed to
	 * {@link NumbersBean#isNumberEven(int)}, a {@link NullPointerException} is
	 * thrown.
	 */
	@Test
	void givenNull_whenCheckingIsNumberEven_thenNullPointerException() {
		Assertions.assertThrows(NullPointerException.class, () -> bean.isNumberEven(null));
	}

}
