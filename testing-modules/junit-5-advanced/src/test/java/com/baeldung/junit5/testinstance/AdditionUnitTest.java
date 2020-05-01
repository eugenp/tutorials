package com.baeldung.junit5.testinstance;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class AdditionUnitTest {

    private int sum = 1;

    @Test
    void addingTwoToSumReturnsThree() {
        sum += 2;
        assertEquals(3, sum);
    }

    @Test
    void addingThreeToSumReturnsFour() {
        sum += 3;
        assertEquals(4, sum);
    }
}
