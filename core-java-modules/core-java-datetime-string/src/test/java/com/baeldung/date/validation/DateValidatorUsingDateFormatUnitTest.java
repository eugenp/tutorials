package com.baeldung.date.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateValidatorUsingDateFormatUnitTest {
    
    @Test
    public void givenValidator_whenValidDatePassed_ThenTrue() {
        DateValidator validator = new DateValidatorUsingDateFormat("MM/dd/yyyy");
        
        assertTrue(validator.isValid("02/28/2019"));
    }
    
    @Test
    public void givenValidator_whenInvalidDatePassed_ThenFalse() {
        DateValidator validator = new DateValidatorUsingDateFormat("MM/dd/yyyy");
        
        assertFalse(validator.isValid("02/30/2019"));
    }
}
