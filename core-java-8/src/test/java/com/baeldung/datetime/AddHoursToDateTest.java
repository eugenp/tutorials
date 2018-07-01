package com.baeldung.datetime;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class AddHoursToDateTest {

    private final AddHoursToDate addHoursToDateObj = new AddHoursToDate();

    @Test
    public void givenJavaUtilDate_whenPositiveHours_thenAddHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 7, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursToJavaUtilDate(actualDate, 2));
    }

    @Test
    public void givenJavaUtilDate_whenNegativeHours_thenMinusHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 3, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursToJavaUtilDate(actualDate, -2));
    }

    @Test
    public void givenJavaUtilDate_whenUsingToInstantAndPostiveHours_thenAddHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 7, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursToDateUsingInstant(actualDate, 2));

    }

    @Test
    public void givenJavaUtilDate_whenUsingToInstantAndNegativeHours_thenAddHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 3, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursToDateUsingInstant(actualDate, -2));
    }

    @Test
    public void givenLocalDateTime_whenUsingAddHoursToLocalDateTime_thenAddHours() {
        LocalDateTime actualDateTime = LocalDateTime.of(2018, Month.JUNE, 25, 5, 0);
        LocalDateTime expectedDateTime = LocalDateTime.of(2018, Month.JUNE, 25, 7, 0);

        assertEquals(expectedDateTime, addHoursToDateObj.addHoursToLocalDateTime(actualDateTime, 2));
    }

    @Test
    public void givenLocalDateTime_whenUsingMinusHoursToLocalDateTime_thenMinusHours() {
        LocalDateTime actualDateTime = LocalDateTime.of(2018, Month.JUNE, 25, 5, 0);
        LocalDateTime expectedDateTime = LocalDateTime.of(2018, Month.JUNE, 25, 3, 0);

        assertEquals(expectedDateTime, addHoursToDateObj.subtractHoursToLocalDateTime(actualDateTime, 2));
    }

    @Test
    public void givenZonedDateTime_whenUsingAddHoursToZonedDateTime_thenAddHours() {
        ZonedDateTime actualZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE, 25, 5, 0), ZoneId.systemDefault());
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE, 25, 7, 0), ZoneId.systemDefault());

        assertEquals(expectedZonedDateTime, addHoursToDateObj.addHoursToZonedDateTime(actualZonedDateTime, 2));
    }

    @Test
    public void givenZonedDateTime_whenUsingMinusHoursToZonedDateTime_thenMinusHours() {
        ZonedDateTime actualZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE, 25, 5, 0), ZoneId.systemDefault());
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDateTime.of(2018, Month.JUNE, 25, 3, 0), ZoneId.systemDefault());

        assertEquals(expectedZonedDateTime, addHoursToDateObj.subtractHoursToZonedDateTime(actualZonedDateTime, 2));
    }

    @Test
    public void givenJavaUtilDate_whenUsingPositiveHrsAndAddHoursWithApacheCommons_thenAddHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 7, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursWithApacheCommons(actualDate, 2));
    }

    @Test
    public void givenJavaUtilDate_whenUsingNegativeHrsAndAddHoursWithApacheCommons_thenMinusHours() {
        Date actualDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 7, 0).getTime();
        Date expectedDate = new GregorianCalendar(2018, Calendar.JUNE, 25, 5, 0).getTime();

        assertEquals(expectedDate, addHoursToDateObj.addHoursWithApacheCommons(actualDate, -2));
    }

    @Test
    public void givenInstant_whenUsingAddHoursToInstant_thenAddHours() {
        Instant actualValue = Instant.parse("2018-06-25T05:12:35Z");
        Instant expectedValue = Instant.parse("2018-06-25T07:12:35Z");

        assertEquals(expectedValue, addHoursToDateObj.addHoursToInstant(actualValue, 2));
    }

    @Test
    public void givenInstant_whenUsingSubtractHoursToInstant_thenMinusHours() {
        Instant actualValue = Instant.parse("2018-06-25T07:12:35Z");
        Instant expectedValue = Instant.parse("2018-06-25T05:12:35Z");

        assertEquals(expectedValue, addHoursToDateObj.subtractHoursToInstant(actualValue, 2));
    }

}
