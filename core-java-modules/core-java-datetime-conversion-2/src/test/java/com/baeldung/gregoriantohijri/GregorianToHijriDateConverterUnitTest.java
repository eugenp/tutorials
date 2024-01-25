package com.baeldung.gregoriantohijri;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

public class GregorianToHijriDateConverterUnitTest {
        @Test
        void givenGregorianDate_whenUsingHijrahChronologyClass_thenConvertHijriDate() {
            LocalDate gregorianDate = LocalDate.of(2013, 3, 31);
            HijrahDate hijriDate = GregorianToHijriDateConverter.usingHijrahChronology(gregorianDate);
            assertEquals(1434, hijriDate.get(ChronoField.YEAR));
            assertEquals(5, hijriDate.get(ChronoField.MONTH_OF_YEAR));
            assertEquals(19, hijriDate.get(ChronoField.DAY_OF_MONTH));
        }

    @Test
    void givenGregorianDate_whenUsingFromMethod_thenConvertHijriDate() {
        LocalDate gregorianDate = LocalDate.of(2013, 3, 31);
        HijrahDate hijriDate = GregorianToHijriDateConverter.usingFromMethod(gregorianDate);
        assertEquals(1434, hijriDate.get(ChronoField.YEAR));
        assertEquals(5, hijriDate.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(19, hijriDate.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    void givenGregorianDate_whenUsingJodaDate_thenConvertHijriDate() {
        DateTime gregorianDate = new DateTime(2013, 3, 31, 0, 0, 0);
        DateTime hijriDate = GregorianToHijriDateConverter.usingJodaDate(gregorianDate);
        assertEquals(1434, hijriDate.getYear());
        assertEquals(5, hijriDate.getMonthOfYear());
        assertEquals(19, hijriDate.getDayOfMonth());
    }

    @Test
    void givenGregorianDate_whenUsingUmmalquraCalendar_thenConvertHijriDate() throws ParseException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2013, Calendar.MARCH, 31);
        UmmalquraCalendar ummalquraCalendar = GregorianToHijriDateConverter.usingUmmalquraCalendar(gregorianCalendar);
        assertEquals(1434, ummalquraCalendar.get(Calendar.YEAR));
        assertEquals(5, ummalquraCalendar.get(Calendar.MONTH) + 1);
        assertEquals(19, ummalquraCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
