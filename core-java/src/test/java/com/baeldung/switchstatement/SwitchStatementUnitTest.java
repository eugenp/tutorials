package com.baeldung.switchstatement;

import org.junit.Test;

import org.junit.Assert;

public class SwitchStatementUnitTest {
    private SwitchStatement s = new SwitchStatement();

    @Test
    public void whenNoBreaks_thenGoThroughBlocks() {
        Assert.assertEquals("unknown animal", s.forgetBreakInSwitch());
    }
    
    @Test(expected=NullPointerException.class)
    public void whenSwitchAgumentIsNull_thenNullPointerException() {
         s.nullValueAsSwitchArgument();
    }
    
    @Test
    public void whenCompareStringsByEqualityOperator_thenIncorrect() {
        Assert.assertEquals(false, s.compareStringsByEqualityOperator());
    }
    
    @Test
    public void whenCompareStrings_thenByEqual() {
        Assert.assertEquals("wild animal", s.compareStringsByEquals());
    }
}
