package com.baeldung.testng.conditionalskip;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SkipExceptionInSetupUnitTest {
    @BeforeClass
    public void givenConditions_whenUsingSkipException_thenSkipTest() {
        if (shouldSkipTest()) {
            throw new SkipException("Skipping tests for UK region");
        }
    }

    public boolean shouldSkipTest() {
        return true;
    }

    @Test
    public void givenSkipConditionInSetup_whenUsingSkipException_thenSkipTest() {
        System.out.println("Dummy Test!");
    }

    @Test
    public void givenSkipConditionInSetup_whenUsingSkipException_thenSkipTest2() {
        System.out.println("Dummy Test 2!");
    }
}
