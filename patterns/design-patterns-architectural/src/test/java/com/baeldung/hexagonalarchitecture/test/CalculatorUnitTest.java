package com.baeldung.hexagonalarchitecture.test;

import com.baeldung.hexagonalarchitecture.application.core.AreaCalculator;
import org.junit.Assert;
import org.junit.Test;

public class CalculatorUnitTest {
    @Test
    public void whenWriteLog_thenLogSaved() {
        FakeTestLogger fakeLogger = new FakeTestLogger();
        AreaCalculator core = new AreaCalculator(fakeLogger);
        core.squareArea(2, 4);
        Assert.assertTrue(fakeLogger.contains("The square area is: 8.0"));
    }
}
