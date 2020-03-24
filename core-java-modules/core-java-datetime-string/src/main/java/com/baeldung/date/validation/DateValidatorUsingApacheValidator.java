package com.baeldung.date.validation;

import org.apache.commons.validator.GenericValidator;

public class DateValidatorUsingApacheValidator implements DateValidator {

    @Override
    public boolean isValid(String dateStr) {
        return GenericValidator.isDate(dateStr, "yyyy-MM-dd", true); 
    }
}
