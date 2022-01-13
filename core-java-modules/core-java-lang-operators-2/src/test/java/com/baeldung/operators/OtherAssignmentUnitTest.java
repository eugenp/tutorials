package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class OtherAssignmentUnitTest {

    @Test
    public void whenUseTheOperatorAssignmentIncrement() {
        
        int number = 10;
        number += 2;

        assertEquals(number, 12);

    }

    @Test
    public void whenUseTheOperatorAssignmentDecrement() {

        int number = 10;
        number -= 2;

        assertEquals(number, 8);

    }

    @Test
    public void whenUseTheOperatorAssignmentMultiplication() {

        int number = 2;
        number *= 10;
        assertEquals(number, 20);

    }

    @Test
    public void whenUseTheOperatorAssignmentDivision() {

        int number = 10;
        number /= 2;
        assertEquals(number, 5);

    }

    @Test
    public void whenUseTheOperatorAssignmentRemaider() {

        int number= 10;
        number %= 2;

        assertEquals(number, 0);

    }
}
