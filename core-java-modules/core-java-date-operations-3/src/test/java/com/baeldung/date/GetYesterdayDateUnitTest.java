package com.baeldung.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Instant;
import org.junit.jupiter.api.Test;

class GetYesterdayDateUnitTest {

    @SuppressWarnings("deprecation")
    @Test
    void givenDate_whenUsingDateClass_thenReturnYesterday() {
        Date currentDate = new Date(2023, Calendar.DECEMBER, 20);
        Date yesterdayDate = new Date(currentDate.getTime() - 24 * 60 * 60 * 1000);
        Date expectedYesterdayDate = new Date(2023, Calendar.DECEMBER, 19);

        assertEquals(expectedYesterdayDate, yesterdayDate);
    }

    @Test
    void givenDate_whenUsingCalendarClass_thenReturnYesterday() {
        Calendar date = new GregorianCalendar(2023, Calendar.APRIL, 20, 4, 0);
        date.add(Calendar.DATE, -1);
        Calendar expectedYesterdayDate = new GregorianCalendar(2023, Calendar.APRIL, 19, 4, 0);

        assertEquals(expectedYesterdayDate, date);
    }

    @Test
    void givenDate_whenUsingLocalDateClass_thenReturnYesterday() {
        LocalDate localDate = LocalDate.of(2023, 12, 20);
        LocalDate yesterdayDate = localDate.minusDays(1);
        LocalDate expectedYesterdayDate = LocalDate.of(2023, 12, 19);

        assertEquals(expectedYesterdayDate, yesterdayDate);
    }

    @Test
    void givenDate_whenUsingInstantClass_thenReturnYesterday() {
        Instant date = Instant.parse("2023-10-25");
        Instant yesterdayDate = date.minus(24 * 60 * 60 * 1000);
        Instant expectedYesterdayDate = Instant.parse("2023-10-24");

        assertEquals(expectedYesterdayDate, yesterdayDate);
    }

    @Test
    void givenDate_whenUsingJodaTimeLocalDateClass_thenReturnYesterday() {
        org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(2023, 12, 20);
        org.joda.time.LocalDate yesterdayDate = localDate.minusDays(1);
        org.joda.time.LocalDate expectedYesterdayDate = new org.joda.time.LocalDate(2023, 12, 19);

        assertEquals(expectedYesterdayDate, yesterdayDate);
    }

    @Test
    void givenDate_whenUsingApacheCommonsLangDateUtils_thenReturnYesterday() {
        Date date = new GregorianCalendar(2023, Calendar.MAY, 16, 4, 0).getTime();
        Date yesterdayDate = DateUtils.addDays(date, -1);
        Date expectedYesterdayDate = new GregorianCalendar(2023, Calendar.MAY, 15, 4, 0).getTime();

        assertEquals(expectedYesterdayDate, yesterdayDate);
    }

}
