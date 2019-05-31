package com.baeldung.error;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorGeneratorUnitTest {

    private ErrorGenerator errorGenerator;

    @Before
    public void setUp() {
        errorGenerator = new ErrorGenerator();
    }

    @Test
    public void whenCheckedException_thenIsCaughtByCatchException() {
        try {
            errorGenerator.throwException();
        } catch (Exception e) {
            // caught! -> test pass
        }
    }

    @Test
    public void whenUncheckedException_thenIsCaughtByCatchException() {
        try {
            errorGenerator.throwRuntimeException();
        } catch (Exception e) {
            // caught! -> test pass
        }
    }

    @Test(expected = Error.class)
    public void whenError_thenIsNotCaughtByCatchException() {
        try {
            errorGenerator.throwError();
        } catch (Exception e) {
            Assert.fail(); // errors are not caught by catch exception
        }
    }

    @Test
    public void whenError_thenIsCaughtByCatchError() {
        try {
            errorGenerator.throwError();
        } catch (Error e) {
            // caught! -> test pass
        }
    }
}