package com.baeldung.junitparams;

import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class SafeAdditionUtilUnitTest {

    private SafeAdditionUtil serviceUnderTest = new SafeAdditionUtil();

    @Test
    @Parameters({ "1, 2, 3", "-10, 30, 20", "15, -5, 10", "-5, -10, -15" })
    public void whenCalledWithAnnotationProvidedParams_thenSafeAddAndReturn(int a, int b, int expectedValue) {
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    @Test
    @Parameters(method = "parametersToTestAdd")
    public void whenCalledWithNamedMethod_thendSafeAddAndReturn(int a, int b, int expectedValue) {
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    private Object[] parametersToTestAdd() {
        return new Object[] { new Object[] { 1, 2, 3 }, new Object[] { -10, 30, 20 }, new Object[] { Integer.MAX_VALUE, 2, Integer.MAX_VALUE }, new Object[] { Integer.MIN_VALUE, -8, Integer.MIN_VALUE } };
    }

    @Test
    @Parameters
    public void whenCalledWithnoParam_thenLoadByNameSafeAddAndReturn(int a, int b, int expectedValue) {
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    private Object[] parametersForWhenCalledWithnoParam_thenLoadByNameSafeAddAndReturn() {
        return new Object[] { new Object[] { 1, 2, 3 }, new Object[] { -10, 30, 20 }, new Object[] { Integer.MAX_VALUE, 2, Integer.MAX_VALUE }, new Object[] { Integer.MIN_VALUE, -8, Integer.MIN_VALUE } };
    }

    @Test
    @Parameters(source = TestDataProvider.class)
    public void whenCalledWithNamedClass_thenSafeAddAndReturn(int a, int b, int expectedValue) {
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

    @Test
    @FileParameters("src/test/resources/JunitParamsTestParameters.csv")
    public void whenCalledWithCsvFile_thenSafeAddAndReturn(int a, int b, int expectedValue) {
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

}
