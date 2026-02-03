package com.baeldung.testng.conditionalskip;

import org.testng.SkipException;
import org.testng.annotations.Test;

public class SkipExceptionInTestUnitTest {
    @Test
    public void givenConditions_whenUsingSkipException_thenSkipTest() {
        if (shouldSkipTest()) {
            throw new SkipException("Skipping test for Demonstration!");
        }
        System.out.println("This line won't get printed");
    }

    public boolean shouldSkipTest() {
        return true;
    }
}
