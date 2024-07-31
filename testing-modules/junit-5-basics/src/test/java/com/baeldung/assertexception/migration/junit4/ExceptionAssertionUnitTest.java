package com.baeldung.assertexception.migration.junit4;

import com.baeldung.assertexception.Executable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.fail;

public class ExceptionAssertionUnitTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(expected = NullPointerException.class)
    public void whenExceptionThrown_thenExpectationSatisfied() {
        String test = null;
        test.length();
    }

    @Test
    public void whenExceptionThrown_thenRuleIsApplied() {
        exceptionRule.expect(NumberFormatException.class);
        exceptionRule.expectMessage("For input string");
        Integer.parseInt("1a");
    }

    @Test
    public void whenNoExceptionIsThrown_thenSucceed() {
        Integer.parseInt("100");
    }

    @Test
    public void givenASpecificExceptionType_whenExecuted_thenEnsureThatExceptionIsNotThrown() {
        assertSpecificExceptionIsNotThrown(IllegalArgumentException.class, () -> {
            throw new ArithmeticException("illegal argument!");
        });
    }

    @Test
    public void givenABlock_whenExecuted_thenEnsureThatNoExceptionAreThrown() {
        assertNoExceptionIsThrown(() -> {
            int d = 100 / 10;
        });
    }

    private <T extends Exception> void assertSpecificExceptionIsNotThrown(Class<T> exceptionClass, Executable executable) {
        try {
            executable.execute();
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                fail(e.getClass().getSimpleName() + " was thrown");
            } else {
                // Any other exception types are ignored and test passes!
                // Logging it here for debugging purpose
                System.out.printf("Caught exception: %s, but ignoring since it it not an instance of %s", e.getClass().getName(), exceptionClass.getName());
            }
        }
    }

    private void assertNoExceptionIsThrown(Executable executable) {
        try {
            executable.execute();
        } catch (Exception e) {
            fail(e.getClass().getSimpleName() + " was thrown");
        }
    }

}
