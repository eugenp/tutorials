package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SwitchPatternTest {

    @Test
    void givenDifferentSqitchPattern_whenCalledBoth_thenReturnTheSameResult() {
        SwitchPattern.SavingsAccount sa = new SwitchPattern.SavingsAccount();
        SwitchPattern.TermAccount ta = new SwitchPattern.TermAccount();
        SwitchPattern.CurrentAccount ca = new SwitchPattern.CurrentAccount();

        assertEquals(SwitchPattern.getBalanceWithOutSwitchPattern(sa), SwitchPattern.getBalanceWithSwitchPattern(sa));
        assertEquals(SwitchPattern.getBalanceWithOutSwitchPattern(ta), SwitchPattern.getBalanceWithSwitchPattern(ta));
        assertEquals(SwitchPattern.getBalanceWithOutSwitchPattern(ca), SwitchPattern.getBalanceWithSwitchPattern(ca));
    }
}