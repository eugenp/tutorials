package com.baeldung.junit5.parameterized;

import java.time.LocalDate;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

class SlashyDateConverter implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new ArgumentConversionException("The argument should be a string: " + source);
        }

        try {
            String[] parts = ((String) source).split("/");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ArgumentConversionException("Failed to convert", e);
        }
    }
}

