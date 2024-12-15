package com.baeldung.booleans;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;
import org.junit.jupiter.api.Test;

public class BooleanEqualityUnitTest {

    @Test
    void givenPrimitiveBooleans_whenUsingDirectComparison_thenCompare() {
        boolean a = true;
        boolean b = true;
        assertTrue(a == b, "Direct comparison of primitive booleans should be true when both are the same");

        a = true;
        b = false;
        assertFalse(a == b, "Direct comparison of primitive booleans should be false when they differ");
    }

    @Test
    void givenBooleans_whenUsingEqualsMethodWithBooleanObjects_thenCompare() {
        Boolean a = Boolean.TRUE;
        Boolean b = Boolean.TRUE;
        assertTrue(a.equals(b), "Using .equals() on Boolean objects should be true when both values are the same");

        a = Boolean.TRUE;
        b = Boolean.FALSE;
        assertFalse(a.equals(b), "Using .equals() on Boolean objects should be false when values differ");
    }

    @Test
    void givenBooleansTypes_whenUsingDirectOperator_thenCompare() {
        Boolean a = Boolean.TRUE;
        Boolean b = new Boolean(true);
        assertFalse(a == b, "Using == on different boolean objects should be false when values differ");
    }

    @Test
    void givenBooleans_whenUsingXORApproach_thenCompare() {
        boolean a = true;
        boolean b = true;
        assertTrue(!(a ^ b), "XOR approach should return true when both values are the same");

        a = true;
        b = false;
        assertFalse(!(a ^ b), "XOR approach should return false when values differ");
    }

    @Test
    void givenBooleans_whenUsingBooleanCompare_thenCompare() {
        boolean a = true;
        boolean b = true;
        assertTrue(Boolean.compare(a, b) == 0, "Boolean.compare() should return 0 when both values are the same");

        a = true;
        b = false;
        assertFalse(Boolean.compare(a, b) == 0, "Boolean.compare() should return non-zero when values differ");
    }

    @Test
    void givenBooleans_whenUsingObjectsEqualsForNullableBooleans_thenCompare() {
        Boolean a = null;
        Boolean b = null;
        assertTrue(Objects.equals(a, b), "Objects.equals() should return true when both Boolean objects are null");

        a = Boolean.TRUE;
        b = null;
        assertFalse(Objects.equals(a, b), "Objects.equals() should return false when one Boolean is null");

        a = Boolean.TRUE;
        b = Boolean.TRUE;
        assertTrue(Objects.equals(a, b), "Objects.equals() should return true when both Boolean values are the same");

        a = Boolean.TRUE;
        b = Boolean.FALSE;
        assertFalse(Objects.equals(a, b), "Objects.equals() should return false when Boolean values differ");
    }
}

