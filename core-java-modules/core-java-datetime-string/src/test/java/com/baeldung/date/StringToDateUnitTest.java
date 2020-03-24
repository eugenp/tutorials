package com.baeldung.date;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringToDateUnitTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectLocalDate() {
        LocalDate expectedLocalDate = LocalDate.of(2018, 05, 05);

        LocalDate date = LocalDate.parse("2018-05-05");

        assertThat(date).isEqualTo(expectedLocalDate);
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectLocalDateTime() {
        LocalDateTime expectedLocalDateTime = LocalDateTime.of(2018, 05, 05, 11, 50, 55);

        LocalDateTime dateTime = LocalDateTime.parse("2018-05-05T11:50:55");

        assertThat(dateTime).isEqualTo(expectedLocalDateTime);
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetDateTimeParseException() {
        thrown.expect(DateTimeParseException.class);
        thrown.expectMessage("Text '2018-05-05' could not be parsed at index 10");

        LocalDateTime.parse("2018-05-05");
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectZonedDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2015, 05, 05, 10, 15, 30);
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Paris"));

        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2015-05-05T10:15:30+01:00[Europe/Paris]");

        assertThat(zonedDateTime).isEqualTo(expectedZonedDateTime);
    }

    @Test
    public void givenDateString_whenConvertedToDateUsingFormatter_thenWeGetCorrectLocalDate() {
        LocalDate expectedLocalDate = LocalDate.of(1959, 7, 9);

        String dateInString = "19590709";
        LocalDate date = LocalDate.parse(dateInString, DateTimeFormatter.BASIC_ISO_DATE);

        assertThat(date).isEqualTo(expectedLocalDate);
    }

    @Test
    public void givenDateString_whenConvertedToDateUsingCustomFormatter_thenWeGetCorrectLocalDate() {
        LocalDate expectedLocalDate = LocalDate.of(1980, 05, 05);

        String dateInString = "Mon, 05 May 1980";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
        LocalDate dateTime = LocalDate.parse(dateInString, formatter);

        assertThat(dateTime).isEqualTo(expectedLocalDate);
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jun-2013";
        Date date = formatter.parse(dateInString);

        assertDateIsCorrect(date);
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetParseException() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        thrown.expect(ParseException.class);
        thrown.expectMessage("Unparseable date: \"07/06/2013\"");

        String dateInString = "07/06/2013";
        formatter.parse(dateInString);
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectJodaDateTime() {
        org.joda.time.format.DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");

        String dateInString = "07/06/2013 10:11:59";
        DateTime dateTime = DateTime.parse(dateInString, formatter);

        assertEquals("Day of Month should be 7: ", 7, dateTime.getDayOfMonth());
        assertEquals("Month should be: ", 6, dateTime.getMonthOfYear());
        assertEquals("Year should be: ", 2013, dateTime.getYear());

        assertEquals("Hour of day should be: ", 10, dateTime.getHourOfDay());
        assertEquals("Minutes of hour should be: ", 11, dateTime.getMinuteOfHour());
        assertEquals("Seconds of minute should be: ", 59, dateTime.getSecondOfMinute());
    }

    @Test
    public void givenDateString_whenConvertedToDate_thenWeGetCorrectDateTime() throws ParseException {
        String dateInString = "07/06-2013";
        Date date = DateUtils.parseDate(dateInString, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM-yyyy" });

        assertDateIsCorrect(date);
    }

    private void assertDateIsCorrect(Date date) {
        Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
        calendar.setTime(date);

        assertEquals("Day of Month should be 7: ", 7, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals("Month should be: ", 5, calendar.get(Calendar.MONTH));
        assertEquals("Year should be: ", 2013, calendar.get(Calendar.YEAR));
    }

}
