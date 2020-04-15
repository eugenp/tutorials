package com.baeldung.extensions.testwatcher;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Assert;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestResultLoggerExtension.class)
class TestWatcherAPIUnitTest {

    @Test
    void givenFalseIsTrue_whenTestAbortedThenCaptureResult() {
        Assumptions.assumeTrue(false);
    }

    @Disabled
    @Test
    void givenTrueIsTrue_whenTestDisabledThenCaptureResult() {
        Assert.assertTrue(true);
    }

    @Test
    void givenTrueIsTrue_whenTestAbortedThenCaptureResult() {
        Assumptions.assumeTrue(true);
    }

    @Disabled("This test is disabled")
    @Test
    void givenFailure_whenTestDisabledWithReason_ThenCaptureResult() {
        fail("Not yet implemented");
    }

}
