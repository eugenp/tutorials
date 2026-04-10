package com.baeldung.junit5.timeout;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;


@Timeout(5)
class TimeoutUnitTest {

	@Test
	@Timeout(1)
	void shouldFailAfterOneSecond() {
		slowMethod();
	}

	@Test
	@Timeout(value = 5, unit = TimeUnit.MINUTES, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
	void shouldUseADifferentThread() {
		System.out.println(Thread.currentThread().getName());
		slowMethod();
	}

	@Test
	void shouldFailAfterDefaultTimeoutOfFiveSeconds() {
		slowMethod();
	}

	@Test
	@Timeout(value = 2, unit = TimeUnit.MINUTES)
	void shouldFailAfterTwoMinutes() {
		slowMethod();
	}

	@Timeout(1)
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5})
	void eachTestShouldFailAfterOneSecond(int input) {
		slowMethod();
	}

	@Nested
	class NestedClassWithoutTimeout {
		@Test
		void shouldFailAfterParentsDefaultTimeoutOfFiveSeconds() {
			slowMethod();
		}
	}

	@Nested
	@Timeout(3)
	class NestedClassWithTimeout {

		@Test
		void shouldFailAfterNestedClassTimeoutOfThreeSeconds() {
			slowMethod();
		}

		@Test
		@Timeout(1)
		void shouldFailAfterOneSecond() {
			slowMethod();
		}
	}

	private void slowMethod() {
		try {
//			Thread.sleep(10_000);
//			just for demonstration purposes
//			tests cannot fail on the pipeline, bue we need failing examples in the article
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
