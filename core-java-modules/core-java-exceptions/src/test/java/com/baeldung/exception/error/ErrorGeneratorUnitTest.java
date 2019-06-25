package com.baeldung.exception.error;

import org.junit.Assert;
import org.junit.Test;

public class ErrorGeneratorUnitTest {

    @Test(expected = AssertionError.class)
    public void whenError_thenIsNotCaughtByCatchException() {
        try {
            throw new AssertionError();
        } catch (Exception e) {
            Assert.fail(); // errors are not caught by catch exception
        }
    }

    @Test
    public void whenError_thenIsCaughtByCatchError() {
        try {
            throw new AssertionError();
        } catch (Error e) {
            // caught! -> test pass
        }
    }
}