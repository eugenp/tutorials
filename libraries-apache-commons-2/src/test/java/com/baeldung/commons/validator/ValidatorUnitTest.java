package com.baeldung.commons.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.CurrencyValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.junit.jupiter.api.Test;

class ValidatorUnitTest {

    @Test
    void givenDate_whenValidationIsCalled_thenChecksDate() {
        DateValidator validator = DateValidator.getInstance();
        String validDate = "28/01/2024";
        String invalidDate = "28/13/2024";

        assertNotNull(validator.validate(validDate, "dd/MM/yyyy"));
        assertTrue(validator.isValid(validDate, "dd/MM/yyyy"));

        assertNull(validator.validate(invalidDate, "dd/MM/yyyy"));
        assertFalse(validator.isValid(invalidDate, "dd/MM/yyyy"));

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2024, Calendar.JANUARY, 28, 10, 30);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = gregorianCalendar.getTime();

        assertEquals("28-Jan-2024", validator.format(date, "dd-MMM-yyyy"));

        TimeZone timeZone = TimeZone.getTimeZone("GMT+5");
        assertEquals("28/01/2024 15:30", validator.format(date, "dd/MM/yyyy HH:mm", timeZone));
    }

    @Test
    void givenNumericString_whenValidationIsCalled_thenReturnsNumber() {
        IntegerValidator validator = IntegerValidator.getInstance();
        String pattern = "00000";
        int number = 1234;

        String formattedNumber = validator.format(number, pattern, Locale.US);

        assertEquals(number, validator.validate(formattedNumber, pattern));
        assertNotNull(validator.validate("123.4", Locale.GERMAN));
    }

    @Test
    void givenCurrencyString_whenValidationIsCalled_thenReturnsCurrency() {
        BigDecimalValidator validator = CurrencyValidator.getInstance();

        assertEquals(new BigDecimal("1234.56"), validator.validate("$1,234.56", Locale.US));
        assertEquals("$1,234.56", validator.format(1234.56, Locale.US));
    }
}
