package com.baeldung.testng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class DataDrivenNameTest {

    private static final Logger logger = LoggerFactory.getLogger(DataDrivenNameTest.class);

    private String testName;

    @BeforeMethod
    public void capture(Method method, Object[] params) {
        String testName = method.getName() + Arrays.toString(params);
        this.testName = testName;
        logger.info("Executing test {}", testName);
    }

    @Test(dataProvider = "numbers")
    public void shouldSquare(int input, int expected) {
        logger.info("Executing scenario from {}", testName);
        assertEquals(Math.pow(input, 2), expected);
    }

    @DataProvider
    public Object[][] numbers() {
        return new Object[][]{{2, 4}, {3, 9}};
    }
}
