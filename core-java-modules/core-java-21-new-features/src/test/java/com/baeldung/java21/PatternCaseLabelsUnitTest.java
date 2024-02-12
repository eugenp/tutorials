package com.baeldung.java21;

import org.junit.Assert;
import org.junit.Test;

public class PatternCaseLabelsUnitTest {

    @Test
    public void whenProcessInputOldWayWithYes_thenReturnOutput() {
        Assert.assertEquals("It's Yes", PatternCaseLabels.processInputOld("Yes"));
    }
    
    @Test
    public void whenProcessInputOldWayWithNo_thenReturnOutput() {
        Assert.assertEquals("It's No", PatternCaseLabels.processInputOld("No"));
    }
    
    @Test
    public void whenProcessInputOldWayWithNull_thenReturnOutput() {
        Assert.assertEquals("Oops, null", PatternCaseLabels.processInputOld(null));
    }
    
    @Test
    public void whenProcessInputOldWayWithInvalidOption_thenReturnOutput() {
        Assert.assertEquals("Try Again", PatternCaseLabels.processInputOld("Invalid Option"));
    }
    
    @Test
    public void whenProcessInputNewWayWithYes_thenReturnOutput() {
        Assert.assertEquals("It's Yes", PatternCaseLabels.processInputNew("Yes"));
    }
    
    @Test
    public void whenProcessInputNewWayWithNo_thenReturnOutput() {
        Assert.assertEquals("It's No", PatternCaseLabels.processInputNew("No"));
    }
    
    @Test
    public void whenProcessInputNewWayWithNull_thenReturnOutput() {
        Assert.assertEquals("Oops, null", PatternCaseLabels.processInputNew(null));
    }
    
    @Test
    public void whenProcessInputNewWayWithInvalidOption_thenReturnOutput() {
        Assert.assertEquals("Try Again", PatternCaseLabels.processInputNew("Invalid Option"));
    }
   
}
