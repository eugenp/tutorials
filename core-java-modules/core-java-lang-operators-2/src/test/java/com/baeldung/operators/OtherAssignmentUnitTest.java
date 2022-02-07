package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class OtherAssignmentUnitTest {

    @Test
    void whenUseTheOperatorAssignmentIncrement_thenSuccess() {
        int number = 10;
        number += 2;
        assertEquals(number, 12);
    }

    @Test
    void whenUseTheOperatorAssignmentDecrement_thenSuccess() {
        int number = 10;
        number -= 2;
        assertEquals(number, 8);
    }

    @Test
    void whenUseTheOperatorAssignmentMultiplication_thenSuccess() {
        int number = 2;
        number *= 10;
        assertEquals(number, 20);
    }

    @Test
    void whenUseTheOperatorAssignmentDivision_thenSuccess() {
        int number = 10;
        number /= 2;
        assertEquals(number, 5);
    }

    @Test
    void whenUseTheOperatorAssignmentRemaider_thenSuccess() {
        int number = 10;
        number %= 2;

        assertEquals(number, 0);
    }
}