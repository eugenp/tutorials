package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SwitchPatternUnitTest {

    @Test
    void whenNoSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        assertEquals(100, SwitchPattern.getBalanceWithOutSwitchPattern(savingsAccount), 0);
    }
    
    @Test
    void whenSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        assertEquals(100, SwitchPattern.getBalanceWithSwitchPattern(savingsAccount), 0);
    }
}
