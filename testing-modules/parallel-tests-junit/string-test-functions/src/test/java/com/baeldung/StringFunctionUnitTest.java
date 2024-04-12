package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringFunctionUnitTest {

    @Test
    public void test_upperCase() {
        assertEquals("TESTCASE", "testCase".toUpperCase());
    }

    @Test
    public void test_indexOf() {
        assertEquals(1, "testCase".indexOf("e"));
    }
}
