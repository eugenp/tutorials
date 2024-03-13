package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PatternCaseLabelsUnitTest {

    @Test
    public void whenProcessInputOldWayWithYes_thenReturnOutput() {
        assertEquals("It's Yes", PatternCaseLabels.processInputOld("Yes"));
    }
    
    @Test
    public void whenProcessInputOldWayWithNo_thenReturnOutput() {
        assertEquals("It's No", PatternCaseLabels.processInputOld("No"));
    }
    
    @Test
    public void whenProcessInputOldWayWithNull_thenReturnOutput() {
        assertEquals("Oops, null", PatternCaseLabels.processInputOld(null));
    }
    
    @Test
    public void whenProcessInputOldWayWithInvalidOption_thenReturnOutput() {
        assertEquals("Try Again", PatternCaseLabels.processInputOld("Invalid Option"));
    }
    
    @Test
    public void whenProcessInputNewWayWithYes_thenReturnOutput() {
        assertEquals("It's Yes", PatternCaseLabels.processInputNew("Yes"));
    }
    
    @Test
    public void whenProcessInputNewWayWithNo_thenReturnOutput() {
        assertEquals("It's No", PatternCaseLabels.processInputNew("No"));
    }
    
    @Test
    public void whenProcessInputNewWayWithNull_thenReturnOutput() {
        assertEquals("Oops, null", PatternCaseLabels.processInputNew(null));
    }
    
    @Test
    public void whenProcessInputNewWayWithInvalidOption_thenReturnOutput() {
        assertEquals("Try Again", PatternCaseLabels.processInputNew("Invalid Option"));
    }
   
}
