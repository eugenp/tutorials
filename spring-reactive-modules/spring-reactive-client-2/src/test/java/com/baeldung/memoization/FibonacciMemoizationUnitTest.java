package com.baeldung.memoization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciMemoizationUnitTest {

    @Test
    void givenFibonacciNumber_whenFirstOccurenceIscache_thenReturnCacheResultOnSecondCall() {
        assertEquals(5, FibonacciMemoization.fibonacci(5));
        assertEquals(2, FibonacciMemoization.fibonacci(3));
        assertEquals(55, FibonacciMemoization.fibonacci(10));
        assertEquals(21, FibonacciMemoization.fibonacci(8));
    }

}