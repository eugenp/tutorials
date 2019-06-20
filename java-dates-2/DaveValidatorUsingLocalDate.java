package com.baeldung.date.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DaveValidatorUsingLocalDate implements DateValidator {
    private DateTimeFormatter dateFormatter;
    
    public DaveValidatorUsingLocalDate(DateTimeFormatter dateFormatter) {
        super();
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean validate(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
