package com.baeldung.algorithms.factorial;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorialUnitTest {

    @Test
    public void givenInteger_whenFindFactorial_thenGetFactorial() {
        int input = 5;
        int expected = 120;
        int actual = Factorial.findFactorial(input);
        assertEquals(expected, actual);
    }

}
