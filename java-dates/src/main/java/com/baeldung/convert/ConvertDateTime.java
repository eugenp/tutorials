package com.baeldung.convert;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ConvertDateTime {

    public static void main(String[] args) {

        java8();
        joda();
    }

    private static void joda() {
        String s = "00:00:01.2";
        DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss.S");
        DateTime dateTime = format.parseDateTime(s);
        System.out.println(dateTime.getMillisOfSecond());
    }

    private static void java8() {
        LocalDateTime localDateTime = LocalDateTime.now();

        ZoneId id = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, id);
        System.out.println(zdt.toInstant().toEpochMilli());
    }
}
