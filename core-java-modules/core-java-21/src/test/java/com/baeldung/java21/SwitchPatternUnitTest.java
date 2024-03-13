package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class SwitchPatternUnitTest {

    @Test
    public void whenNoSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        assertEquals(100, SwitchPattern.getBalanceWithOutSwitchPattern(savingsAccount), 0);
    }
    
    @Test
    public void whenSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        assertEquals(100, SwitchPattern.getBalanceWithSwitchPattern(savingsAccount), 0);
    }
}
