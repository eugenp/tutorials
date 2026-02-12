package com.baeldung.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NameByXmlSuiteTest {

    private static final Logger logger = LoggerFactory.getLogger(NameByXmlSuiteTest.class);

    private String iTestContextSuiteName;

    @BeforeTest
    public void xmlName(ITestContext ctx) {
        iTestContextSuiteName = ctx.getName();
        logger.info("Starting test suite: {}", ctx.getName());
    }

    @Test
    public void givenSimpleAssertionThenShouldPass() {
        logger.info("Executing scenario from {}", iTestContextSuiteName);
        Assert.assertEquals(4, 2 + 2);
    }
}
