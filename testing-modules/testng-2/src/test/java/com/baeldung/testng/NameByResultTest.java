package com.baeldung.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NameByResultTest {

    private static final Logger logger = LoggerFactory.getLogger(NameByResultTest.class);
    private String testName;

    @BeforeMethod
    public void capture(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        this.testName = testName;
    }

    @Test
    public void givenTestNameSetup_whenTestNameIsRequested_thenShouldReturnTestName() {
        logger.info("Executing scenario {}", testName);
        Assert.assertEquals(testName, "givenTestNameSetup_whenTestNameIsRequested_thenShouldReturnTestName");
    }
}
