package com.baeldung.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class NameByReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(NameByReflectionTest.class);
    private String testName;

    @BeforeMethod
    public void capture(Method method) {
        String testName = method.getName();
        this.testName = testName;
    }

    @Test
    public void givenTestNameSetup_whenTestNameIsRequested_thenShouldReturnTestName() {
        logger.info("Executing scenario {}", testName);
        Assert.assertEquals(testName, "givenTestNameSetup_whenTestNameIsRequested_thenShouldReturnTestName");
    }
}
