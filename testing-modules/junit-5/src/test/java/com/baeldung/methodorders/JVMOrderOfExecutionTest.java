package com.baeldung.methodorders;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class JVMOrderOfExecutionTest {

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
}