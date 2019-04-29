package com.baeldung.bifunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class BiFunctionUnitTest {

    @Test
    public void givenStreamOfIntegersThenCalculateSumUsingReferenceMethod() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        int result = stream.reduce(0, Integer::sum);
        assertEquals(15, result);
    }

    @Test
    public void givenStreamOfIntegersThenCalculateSumUsingMapMethod() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        int result = stream.map(a -> a + 2)
            .reduce(1, (a, b) -> a * b);
        assertEquals(2520, result);
    }

    @Test
    public void givenStreamOfIntegersThenCalculateSumUsingBiFunction() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        int result = stream.reduce(0, (a, b) -> ((a * b) + 2) - (b + 1));
        assertEquals(-119, result);
    }

    @Test
    public void givenTwoIntegersThenCalculateSumUsingCalculator() {
        Calculator calculator = new Calculator(3, 7);
        Integer result = calculator.calculate((a, b) -> a + b);
        assertEquals(10, result.intValue());
    }

    @Test
    public void givenTwoIntegersThenCalculateComplcatedOperationUsingCalculator() {
        Calculator calculator = new Calculator(3, 7);
        Integer result = calculator.calculate((a, b) -> (a * a) * (b + 10));
        assertEquals(153, result.intValue());
    }
}
