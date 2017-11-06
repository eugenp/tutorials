package com.baeldung;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
public class JVMOrderOfExecutionTest {
    @Rule
    public TestName testName = new TestName();

    @Test
    public void secondTest() {
        printMethodName();
    }

    @Test
    public void thirdTest() {
        printMethodName();
    }

    @Test
    public void firstTest() {
        printMethodName();
    }

    private void printMethodName() {
        System.out.println(testName.getMethodName());
    }
}