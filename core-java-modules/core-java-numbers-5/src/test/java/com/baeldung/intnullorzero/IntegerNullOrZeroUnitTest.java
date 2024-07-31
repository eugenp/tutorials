package com.baeldung.intnullorzero;

import static com.baeldung.IntegerNullOrZero.usingObjectUtils;
import static com.baeldung.IntegerNullOrZero.usingOptional;
import static com.baeldung.IntegerNullOrZero.usingStandardWay;
import static com.baeldung.IntegerNullOrZero.usingTernaryOperator;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntegerNullOrZeroUnitTest {

    @Test
    void givenInts_whenUsingStandardWay_thenGetExpectedResult() {
        int n0 = 0;
        boolean result0 = usingStandardWay(n0);
        assertTrue(result0);

        boolean resultNull = usingStandardWay(null);
        assertTrue(resultNull);

        int n42 = 42;
        boolean result42 = usingStandardWay(n42);
        assertFalse(result42);
    }

    @Test
    void givenInts_whenUsingTernaryOperator_thenGetExpectedResult() {
        int n0 = 0;
        boolean result0 = usingTernaryOperator(n0);
        assertTrue(result0);

        boolean resultNull = usingTernaryOperator(null);
        assertTrue(resultNull);

        int n42 = 42;
        boolean result42 = usingTernaryOperator(n42);
        assertFalse(result42);
    }

    @Test
    void givenInts_whenUsingOptional_thenGetExpectedResult() {
        int n0 = 0;
        boolean result0 = usingOptional(n0);
        assertTrue(result0);

        boolean resultNull = usingOptional(null);
        assertTrue(resultNull);

        int n42 = 42;
        boolean result42 = usingOptional(n42);
        assertFalse(result42);
    }

    @Test
    void givenInts_whenUsingObjectUtils_thenGetExpectedResult() {
        int n0 = 0;
        boolean result0 = usingObjectUtils(n0);
        assertTrue(result0);

        boolean resultNull = usingObjectUtils(null);
        assertTrue(resultNull);

        int n42 = 42;
        boolean result42 = usingObjectUtils(n42);
        assertFalse(result42);
    }
}