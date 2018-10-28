package com.java.src;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoundUpToHundredTest {
    @Test
    public void givenInput_whenRound_thenRoundUpToTheNearestHundred() {
        assertEquals("Rounded up to hundred", 100, RoundUpToHundred.round(99));
        assertEquals("Rounded up to three hundred ", 300, RoundUpToHundred.round(200.2));
        assertEquals("Returns same rounded value", 400, RoundUpToHundred.round(400));
    }
}
