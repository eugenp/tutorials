package com.baeldung.testng;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenNameTest {

    private static final Logger logger = LoggerFactory.getLogger(DataDrivenNameTest.class);
    private String testName;

    @BeforeMethod
    public void capture(Method method, Object[] params) {
        String testName = method.getName() + Arrays.toString(params);
        this.testName = testName;
    }

    @Test(dataProvider = "numbers")
    public void givenInputParameters_WhenFetchingTestName_ThenShouldReturnCorrectTestName(int input, int expected) {
        logger.info("Executing scenario from {}", testName);
        Assert.assertListContainsObject(List.of("givenInputParameters_WhenFetchingTestName_ThenShouldReturnCorrectTestName[2, 4]", "givenInputParameters_WhenFetchingTestName_ThenShouldReturnCorrectTestName[3, 9]"), testName, "Test name is not as expected");
    }

    @DataProvider
    public Object[][] numbers() {
        return new Object[][] { { 2, 4 }, { 3, 9 } };
    }
}
