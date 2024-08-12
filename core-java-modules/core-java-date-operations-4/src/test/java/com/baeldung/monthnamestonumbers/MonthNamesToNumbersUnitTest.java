package com.baeldung.monthnamestonumbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;

class MonthNamesToNumbersUnitTest {

    @Test
    void givenMonthName_whenUsingDateLegacyAPI_thenReturnNumber() throws ParseException {
        String givenMonthName = "May";
        int expectedMonthNumber = 5;

        Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(givenMonthName);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int monthNumber = calendar.get(Calendar.MONTH) + 1;

        assertEquals(expectedMonthNumber, monthNumber);
    }

    @Test
    void givenMonthName_whenUsingChronoFieldEnum_thenReturnNumber() {
        String givenMonthName = "Sep";
        int expectedMonthNumber = 9;

        int monthNumber = DateTimeFormatter.ofPattern("MMM")
            .withLocale(Locale.ENGLISH)
            .parse(givenMonthName)
            .get(ChronoField.MONTH_OF_YEAR);

        assertEquals(expectedMonthNumber, monthNumber);
    }

    @Test
    void givenMonthName_whenUsingMonthEnum_thenReturnNumber() {
        String givenMonthName = "October";
        int expectedMonthNumber = 10;

        int monthNumber = Month.valueOf(givenMonthName.toUpperCase())
            .getValue();

        assertEquals(expectedMonthNumber, monthNumber);
    }

    @Test
    void givenMonthName_whenUsingJodaTime_thenReturnNumber() {
        String givenMonthName = "April";
        int expectedMonthNumber = 4;

        int monthNumber = DateTimeFormat.forPattern("MMM")
            .withLocale(Locale.ENGLISH)
            .parseDateTime(givenMonthName)
            .getMonthOfYear();

        assertEquals(expectedMonthNumber, monthNumber);
    }

}
