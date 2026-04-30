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
    public void fetchSuiteNameFromITestContext(ITestContext ctx) {
        iTestContextSuiteName = ctx.getName();
    }

    @Test
    public void givenTestContextSetup_whenRequestingSuiteName_thenShouldReturnSuiteName() {
        logger.info("Executing scenario from {}", iTestContextSuiteName);
        Assert.assertEquals(iTestContextSuiteName, "All Tests");
    }
}
