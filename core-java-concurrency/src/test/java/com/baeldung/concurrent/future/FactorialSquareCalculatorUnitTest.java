package com.baeldung.concurrent.future;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

public class FactorialSquareCalculatorUnitTest {

    @Test
    public void whenCalculatesFactorialSquare_thenReturnCorrectValue() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

        forkJoinPool.execute(calculator);

        assertEquals("The sum of the squares from 1 to 10 is 385", 385, calculator.join().intValue());
    }

}
