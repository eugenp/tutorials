package com.baeldung.datetostring;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateToStringFormatterUnitTest {

    private static final String DATE_FORMAT = "MMM d, yyyy HH:mm a";
    private static Date date;

    @BeforeClass
    public static void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("CET"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.AUGUST, 1, 12, 0);
        date = calendar.getTime();
    }

    @Test
    public void whenDateConvertedUsingSimpleDateFormatToString_thenCorrect() {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String formattedDate = formatter.format(date);
        String expectedDate = "Aug 1, 2018 12:00 pm";
        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void whenDateConvertedUsingDateFormatToString_thenCorrect() {
        String formattedDate = DateFormat
          .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.US)
          .format(date);
        String expectedDate = "Aug 1, 2018, 12:00 PM";
        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void whenDateConvertedUsingFormatterToString_thenCorrect() {
        String formattedDate = String.format("%1$tb %1$te, %1$tY %1$tI:%1$tM %1$Tp", date);
        String expectedDate = "Aug 1, 2018 12:00 PM";
        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void whenDateConvertedUsingDateTimeApiToString_thenCorrect() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Instant instant = date.toInstant();
        LocalDateTime ldt = instant
          .atZone(ZoneId.of("CET"))
          .toLocalDateTime();
        String formattedDate = ldt.format(fmt);
        String expectedDate = "Aug 1, 2018 12:00 pm";
        assertEquals(expectedDate, formattedDate);
    }
}
