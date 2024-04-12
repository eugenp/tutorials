package com.baeldung.jqwik;

import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DivisionLiveTest {
    @Property
    public void divideBySelf(@ForAll int value) {
        int result = divide(value, value);
        assertEquals(result, 1);
    }

    @Property
    public void dividePositiveBySelf(@ForAll @Positive int value) {
        int result = divide(value, value);
        assertEquals(result, 1);
    }

    @Property
    public void divideNonZeroBySelf(@ForAll("nonZeroNumbers") int value) {
        int result = divide(value, value);
        assertEquals(result, 1);
    }

    @Property
    public void divideLargeBySmall(@ForAll @Positive int a, @ForAll @Positive int b) {
        Assume.that(a > b);

        int result = divide(a, b);
        assertTrue(result >= 1);
    }

    @Provide
    Arbitrary<Integer> nonZeroNumbers() {
        return Arbitraries.integers()
            .filter(v -> v != 0);
    }

    private int divide(int a, int b) {
        return a / b;
    }
}
