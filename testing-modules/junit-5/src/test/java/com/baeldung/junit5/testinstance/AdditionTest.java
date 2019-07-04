package com.baeldung.junit5.testinstance;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class AdditionTest {

    private int sum = 1;

    @Test
    void addingTwoToSumReturnsThree() {
        sum += 2;
        assertEquals(3, sum);
    }

    @Test
    void addThreeToSumReturnsFour() {
        sum += 3;
        assertEquals(4, sum);
    }
}
