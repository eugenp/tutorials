package com.baeldung.vavr.either;

import io.vavr.control.Either;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EitherUnitTest {

    @Test
    public void givenMarks_whenPassNumber_thenExpectNumber() {
        Either<String, Integer> result = EitherDemo.computeWithEither(100);
        int marks = result.right()
          .getOrElseThrow(x -> new IllegalStateException());

        assertEquals(100, marks);
    }

    @Test
    public void givenMarks_whenFailNumber_thenExpectErrorMesssage() {
        Either<String, Integer> result = EitherDemo.computeWithEither(50);
        String error = result.left()
          .getOrNull();

        assertEquals("Marks not acceptable", error);
    }

    @Test
    public void givenPassMarks_whenModified_thenExpectNumber() {
        Either<String, Integer> result = EitherDemo.computeWithEither(90);
        int marks = result.right()
          .map(x -> x * 2)
          .get();

        assertEquals(180, marks);
    }

}
