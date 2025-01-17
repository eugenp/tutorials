package com.baeldung.localdatetoiso;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class LocalDateToISOUnitTest {
    @Test
    public void givenLocalDate_whenUsingDateTimeFormatter_thenISOFormat(){
        LocalDateToISO localDateToISO = new LocalDateToISO();
        LocalDate localDate = LocalDate.of(2023, 11, 6);

        String expected = "2023-11-06T00:00:00.000Z";
        String actual = localDateToISO.formatUsingDateTimeFormatter(localDate);
        assertEquals(expected, actual);
    }

    @Test
    public void givenLocalDate_whenUsingSimpleDateFormat_thenISOFormat(){
        LocalDateToISO localDateToISO = new LocalDateToISO();
        LocalDate localDate = LocalDate.of(2023, 11, 6);

        String expected = "2023-11-06T00:00:00.000Z";
        String actual = localDateToISO.formatUsingSimpleDateFormat(localDate);
        assertEquals(expected, actual);
    }

    @Test
    public void givenLocalDate_whenUsingJodaTime_thenISOFormat() {
        LocalDateToISO localDateToISO = new LocalDateToISO();
        org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(2023, 11, 6);

        String expected = "2023-11-06T00:00:00.000Z";
        String actual = localDateToISO.formatUsingJodaTime(localDate);
        assertEquals(expected, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void givenLocalDate_whenUsingApacheCommonsLang_thenISOFormat() {
        LocalDateToISO localDateToISO = new LocalDateToISO();
        LocalDate localDate = LocalDate.of(2023, 11, 6);

        String expected = "2023-11-06T00:00:00.000Z";
        String actual = localDateToISO.formatUsingApacheCommonsLang(localDate);
        assertEquals(expected, actual);
    }
}
