package com.baeldung.java21;

import org.junit.Assert;
import org.junit.Test;

public class SwitchPatternUnitTest {

    @Test
    public void whenNoSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        Assert.assertEquals(100, SwitchPattern.getBalanceWithOutSwitchPattern(savingsAccount), 0);
    }
    
    @Test
    public void whenSwitchPattern_thenReturnSavingsAccountBalance() {
        SwitchPattern.SavingsAccount savingsAccount = new SwitchPattern.SavingsAccount();
        Assert.assertEquals(100, SwitchPattern.getBalanceWithSwitchPattern(savingsAccount), 0);
    }
}
