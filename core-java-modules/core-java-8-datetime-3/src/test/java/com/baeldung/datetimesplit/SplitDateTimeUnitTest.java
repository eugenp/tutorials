package com.baeldung.datetimesplit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class SplitDateTimeUnitTest {

    @Test
    void givenDateTimeString_whenFormatsKnown_thenSplitIntoDateAndTime() {
        String dateTimeStr = "8/29/2011 11:16:12 AM";
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a", Locale.ENGLISH);
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter format3 = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = dateTimeFormatterBuilder.appendOptional(format2).appendOptional(format1).appendOptional(format3).toFormatter(Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

//        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, format1);
//        DateTimeFormatter formatter = dateTimeFormatterBuilder.append(format1).toFormatter(Locale.ENGLISH);
        System.out.println(dateTime);
    }
}
