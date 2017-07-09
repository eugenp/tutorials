package com.baeldung.vavr.either;

import org.junit.Test;

import io.vavr.control.Either;

import static org.junit.Assert.assertEquals;

public class EitherUnitTest {

	@Test
	public void givenMarks_whenPassNumber_thenExpectNumber() {
		Either<String, Integer> result = EitherDemo.computeWithEither(100);
		int marks = EitherDemo.getMarks(result);
		assertEquals(100, marks);
	}

	@Test
	public void givenMarks_whenFailNumber_thenExpectErrorMesssage() {
		Either<String, Integer> result = EitherDemo.computeWithEither(50);
		String error = EitherDemo.getError(result);
		assertEquals("Marks not acceptable", error);
	}

	@Test
	public void givenPassMarks_whenModified_thenExpectNumber() {
		Either<String, Integer> result = EitherDemo.computeWithEither(90);
		int marks = EitherDemo.getModifiedMarks(result);
		assertEquals(180, marks);
	}

}
