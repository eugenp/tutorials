package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class OtherAssignmentUnitTest {

    @Test
    void whenUseTheOperatorAssignmentIncrement() {
        int number = 10;
        number += 2;
        assertEquals(number, 12);

    }

    @Test
    void whenUseTheOperatorAssignmentDecrement() {
        int number = 10;
        number -= 2;
        assertEquals(number, 8);

    }

    @Test
    void whenUseTheOperatorAssignmentMultiplication() {
        int number = 2;
        number *= 10;
        assertEquals(number, 20);

    }

    @Test
    void whenUseTheOperatorAssignmentDivision() {
        int number = 10;
        number /= 2;
        assertEquals(number, 5);

    }

    @Test
    void whenUseTheOperatorAssignmentRemaider() {
        int number = 10;
        number %= 2;

        assertEquals(number, 0);

    }
}
