package com.baeldung.methodorders;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NameAscendingOrderOfExecutionUnitTest {
    private static StringBuilder output = new StringBuilder("");

    @Test
    public void secondTest() {
        output.append("b");
    }

    @Test
    public void thirdTest() {
        output.append("c");
    }

    @Test
    public void firstTest() {
        output.append("a");
    }

    @AfterClass
    public static void assertOutput() {
        assertEquals(output.toString(), "abc");
    }
}
