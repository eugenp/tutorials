package com.baeldung.finallykeyword;

import static org.junit.Assert.*;

import org.junit.Test;

public class PitfallsWhenUsingFinallyUnitTest {

    PitfallsWhenUsingFinally instance = new PitfallsWhenUsingFinally();

    @Test
    public void testIgnoresException() {
        String result = instance.disregardsUnCaughtException();
        assertEquals("from finally", result);
    }

    @Test
    public void testIgnoresOtherReturns() {
        String result = instance.ignoringOtherReturns();
        assertEquals("from finally", result);
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsException() {
        instance.throwsException();
    }
}
