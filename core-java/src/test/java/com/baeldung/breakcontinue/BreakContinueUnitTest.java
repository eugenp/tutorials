package com.baeldung.breakcontinue;

import static com.baeldung.breakcontinue.BreakContinue.labeledBreak;
import static com.baeldung.breakcontinue.BreakContinue.labeledContinue;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledBreak;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledBreakNestedLoops;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledContinue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 测试：
 * Break
 * Continue
 */
public class BreakContinueUnitTest {

    @Test
    public void whenUnlabeledBreak_ThenEqual() {
        assertEquals(4, unlabeledBreak());
    }

    @Test
    public void whenUnlabeledBreakNestedLoops_ThenEqual() {
        assertEquals(2, unlabeledBreakNestedLoops());
    }

    @Test
    public void whenLabeledBreak_ThenEqual() {
        assertEquals(1, labeledBreak());
    }

    @Test
    public void whenUnlabeledContinue_ThenEqual() {
        assertEquals(2, unlabeledContinue());
    }

    @Test
    public void whenLabeledContinue_ThenEqual() {
        assertEquals(2, labeledContinue());
    }

}
