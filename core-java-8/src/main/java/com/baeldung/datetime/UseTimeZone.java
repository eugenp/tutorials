package com.baeldung.datetime;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class UseTimeZone {

    public static String setTimeZoneUsingJava7(String pattern, String timeZone) {
        SimpleDateFormat isoFormat = new SimpleDateFormat(pattern);
        TimeZone tzone = TimeZone.getTimeZone(timeZone);
        isoFormat.setTimeZone(tzone);
        String time = isoFormat.format(new Date());
        System.out.println(time);
        return time;
    }

    public static String setTimeZoneUsingJava8(String pattern, String timeZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        String time = now.format(formatter);
        System.out.println(time);
        return time;
    }

    public static String setTimeZoneUsingJodaTime(String pattern, String timeZone) {
        DateTimeZone tzone = DateTimeZone.forID(timeZone);
        DateTime dt = new DateTime(tzone);
        String time = dt.toString(pattern);
        System.out.println(time);
        return time;
    }

}
