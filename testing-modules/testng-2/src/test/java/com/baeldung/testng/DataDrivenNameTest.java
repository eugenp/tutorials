package com.baeldung.testng;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class DataDrivenNameTest {

    @BeforeMethod
    public void capture(Method method, Object[] params) {
        String testName = method.getName() + Arrays.toString(params);
        System.out.println("→ " + testName);
    }

    @Test(dataProvider = "numbers")
    public void shouldSquare(int input, int expected) {
        assertEquals(Math.pow(input, 2), expected);
    }

    @DataProvider
    public Object[][] numbers() {
        return new Object[][]{{2, 4}, {3, 9}};
    }
}
