package com.baeldung.junit;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class FailAssertionUnitTest {

    public static final Random RANDOM = new Random();

    @Test
    @Ignore
    public void incompleteTest() {
        fail("Not yet implemented");
    }

    @Test
    public void expectedException() {
        try {
            methodThrowsException();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void unexpectedException() {
        try {
            safeMethod();
            // more testing code
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }

    @Test
    public void testingCondition() {
        int result = randomInteger();
        if (result > Integer.MAX_VALUE) {
            fail("Result cannot exceed integer max value");
        }
        // more testing code
    }

    @Test
    public void returnBefore() {
        int value = randomInteger();
        for (int i = 0; i < 5; i++) {
            // returns when (value + i) is an even number
            if ((i + value) % 2 == 0) {
                return;
            }
        }
        fail("Should have returned before");
    }

    private void safeMethod() {
    }

    private void methodThrowsException() throws Exception {
        throw new Exception();
    }

    private int randomInteger() {
        return RANDOM.nextInt(Integer.MAX_VALUE);
    }
}
