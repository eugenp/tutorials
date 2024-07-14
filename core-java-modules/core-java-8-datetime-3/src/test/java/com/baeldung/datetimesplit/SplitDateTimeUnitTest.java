package com.baeldung.datetimesplit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplitDateTimeUnitTest {

    @Test
    void givenDateTimeString_whenFormatsKnown_thenSplitIntoDateAndTime() {

        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss.SSS", Locale.ENGLISH);
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter format3 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        DateTimeFormatter multiFormatter = dateTimeFormatterBuilder
                .appendOptional(format1)
                .appendOptional(format2)
                .appendOptional(format3)
                .toFormatter(Locale.ENGLISH);

        //case 1
        LocalDateTime dateTime1 = LocalDateTime.parse("2024-07-04 11:15:24", multiFormatter);
        String date1 = dateTime1.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time1 = dateTime1.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        assertEquals("2024-07-04", date1);
        assertEquals("11:15:24", time1);
        //case 2
        LocalDateTime dateTime2 = LocalDateTime.parse("04-07-2024T11:15:24.123", multiFormatter);
        String date2 = dateTime2.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time2 = dateTime2.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        assertEquals("2024-07-04", date2);
        assertEquals("11:15:24.123", time2);
        //case 3
        LocalDateTime dateTime3 = LocalDateTime.parse("04-07-2024 11:15:24", multiFormatter);
        String date3 = dateTime3.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy"));
        String time3 = dateTime3.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        assertEquals("04-07-2024", date3);
        assertEquals("11:15:24", time3);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-07-04 11:15:24,2024-07-04,11:15:24",
            "8/29/2011 11:16:12 AM,8/29/2011,11:16:12 AM",
            "2024-07-04 11:15:24.987,2024-07-04,11:15:24.987",
            "01-07-2024 11:15:24.987,01-07-2024,11:15:24.987"
    })
    void givenDateTimeString_whenUsingSplit_thenGetDateAndTimeParts(String dateTimeStr, String expectedDate, String expectedTime) {
        String[] split = dateTimeStr.split("\\s", 2);
        assertEquals(2, split.length);
        assertEquals(expectedDate, split[0]);
        assertEquals(expectedTime, split[1]);
    }

    @Test
    void givenDateTimeString_whenUsingSplit_thenGetDateAndTimePartsForADifferentPattern() {
        String dateTimeStr = "2024/07/04 11:15:24.233";
        String[] split = dateTimeStr.split("\\s");
        assertEquals(2, split.length);
        assertEquals("2024/07/04", split[0]);
        assertEquals("11:15:24.233", split[1]);
    }

    @Test
    void givenDateTimeString_whenUsingDateTimeFormatter_thenSeparateDateAndTimeParts() {
        String dateTimeStr = "2024-07-04 11:15:24";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, format);
        assertEquals("2024-07-04", dateTime.toLocalDate().format(dateFormat));
        assertEquals("11:15:24", dateTime.toLocalTime().format(timeFormat));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-07-04 11:15:24,2024-07-04,11:15:24",
            "2024-07-04 11:15:24.987,2024-07-04,11:15:24.987"
    })
    void givenDateTimeString_whenUsingRegex_thenGetDateAndTimeParts(String dateTimeStr, String expectedDate, String expectedTime) {
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})\\s(\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?)");
        Matcher matcher = pattern.matcher(dateTimeStr);
        assertTrue(matcher.matches());
        assertEquals(expectedDate, matcher.group(1));
        assertEquals(expectedTime, matcher.group(2));
    }

}
