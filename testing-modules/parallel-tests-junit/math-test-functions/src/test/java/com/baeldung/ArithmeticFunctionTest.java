package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArithmeticFunctionTest {

    @Test
    public void test_addingIntegers_returnsSum() {
        assertEquals(22, Math.addExact(10, 12));
    }

    @Test
    public void test_multiplyingIntegers_returnsProduct() {
        assertEquals(120, Math.multiplyExact(10, 12));
    }

    @Test
    public void test_subtractingIntegers_returnsDifference() {
        assertEquals(2, Math.subtractExact(12, 10));
    }

    @Test
    public void test_minimumInteger() {
        assertEquals(10, Math.min(10, 12));
    }
}
