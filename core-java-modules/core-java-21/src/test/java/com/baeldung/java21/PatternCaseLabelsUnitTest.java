package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PatternCaseLabelsUnitTest {

    @Test
    void whenProcessInputOldWayWithYes_thenReturnOutput() {
        assertEquals("It's Yes", PatternCaseLabels.processInputOld("Yes"));
    }
    
    @Test
    void whenProcessInputOldWayWithNo_thenReturnOutput() {
        assertEquals("It's No", PatternCaseLabels.processInputOld("No"));
    }
    
    @Test
    void whenProcessInputOldWayWithNull_thenReturnOutput() {
        assertEquals("Oops, null", PatternCaseLabels.processInputOld(null));
    }
    
    @Test
    void whenProcessInputOldWayWithInvalidOption_thenReturnOutput() {
        assertEquals("Try Again", PatternCaseLabels.processInputOld("Invalid Option"));
    }
    
    @Test
    void whenProcessInputNewWayWithYes_thenReturnOutput() {
        assertEquals("It's Yes", PatternCaseLabels.processInputNew("Yes"));
    }
    
    @Test
    void whenProcessInputNewWayWithNo_thenReturnOutput() {
        assertEquals("It's No", PatternCaseLabels.processInputNew("No"));
    }
    
    @Test
    void whenProcessInputNewWayWithNull_thenReturnOutput() {
        assertEquals("Oops, null", PatternCaseLabels.processInputNew(null));
    }
    
    @Test
    void whenProcessInputNewWayWithInvalidOption_thenReturnOutput() {
        assertEquals("Try Again", PatternCaseLabels.processInputNew("Invalid Option"));
    }
   
}
