package com.baeldung.breakloop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoopBreakingUnitTest {

    private LoopBreaking loopBreaking = new LoopBreaking();

    @Test
    void whenUsingBreak_shouldBreakInnerLoop() {
        assertEquals("outer0inner0outer1inner0", loopBreaking.simpleBreak());
    }

    @Test
    void whenUsingLabeledBreak_shouldBreakInnerLoopAndOuterLoop() {
        assertEquals("outer0inner0", loopBreaking.labelBreak());
    }

    @Test
    void whenUsingReturn_shouldBreakInnerLoopAndOuterLoop() {
        assertEquals("outer0inner0", loopBreaking.usingReturn());
    }
}