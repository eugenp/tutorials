package com.baeldung.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class FactoryNameTest {
    private static final Logger logger = LoggerFactory.getLogger(FactoryNameTest.class);
    private final String instanceLabel;

    public FactoryNameTest(String label) {
        this.instanceLabel = label;
    }

    @Factory
    public static Object[] build() {
        return new Object[]{new FactoryNameTest("fast-path"), new FactoryNameTest("slow-path")};
    }

    @BeforeMethod
    public void capture(Method method, ITestResult result) {
        String fullName = method.getName() + "[" + instanceLabel + "]";
        result.setAttribute("displayName", fullName);
        logger.info("capturing {}", fullName);
    }

    @Test
    public void givenSimpleAssertionThenShouldPass() {
        logger.info("Executing scenario {}", instanceLabel);
        Assert.assertEquals(4, 2 + 2);
    }
}
