package com.baeldung;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.DEFAULT)
public class DefaultOrderOfExecutionTest {
    @Rule
    public TestName testName = new TestName();

    @Test
    public void secondTest() {
        printHashCode();
    }

    @Test
    public void thirdTest() {
        printHashCode();
    }

    @Test
    public void firstTest() {
        printHashCode();
    }

    private void printHashCode() {
        System.out.println(testName.getMethodName() + ", hash = " + testName.getMethodName()
            .hashCode());
    }
}
