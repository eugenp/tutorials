package com.baeldung.datetime;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DisplayLastTwoDigitsOfCurrentYearUnitTest {

    private static final String EXPECTED = Year.now().format(DateTimeFormatter.ofPattern("uu"));

    @Test
    public void whenUsingCalendarModulo_thenReturnsCorrectTwoDigitYear() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int lastTwo = currentYear % 100;
        String result = (lastTwo < 10) ? "0" + lastTwo : String.valueOf(lastTwo);

        assertEquals(EXPECTED, result);
    }

    @Test
    public void whenUsingSimpleDateFormat_thenReturnsCorrectTwoDigitYear() {
        DateFormat df = new SimpleDateFormat("yy");
        String result = df.format(new Date());

        assertEquals(EXPECTED, result);
    }

    @Test
    public void whenUsingJavaTimeAPI_thenReturnsCorrectTwoDigitYear() {
        String result = Year.now().format(DateTimeFormatter.ofPattern("uu"));

        assertEquals(EXPECTED, result);
    }

}
