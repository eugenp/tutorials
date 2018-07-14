package com.baeldung.datetime;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class UseTimeZone {
    
    private UseTimeZone() {
        
    }

    private static final String PATTERN = "E yyyy-MM-dd HH:mm:ss a";

    public static TimeZone setTimeZoneUsingJava7(String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        TimeZone tzone = TimeZone.getTimeZone(timeZone);
        calendar.setTimeZone(tzone);

        System.out.println(String.format("Java7: Time now in '%s' is '%s'", 
            calendar.getTimeZone().getID(), simpleDateFormat.format(calendar.getTime())));

        return calendar.getTimeZone();
    }

    public static ZoneId setTimeZoneUsingJava8(String timeZone) {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);

        System.out.println(String.format("Java8: Time now in '%s' is '%s'", 
            zonedDateTime.getZone(), zonedDateTime.format(DateTimeFormatter.ofPattern(PATTERN))));

        return zonedDateTime.getZone();
    }

    public static DateTimeZone setTimeZoneUsingJodaTime(String timeZone) {
        DateTimeZone tzone = DateTimeZone.forID(timeZone);
        DateTime dateTime = new DateTime(tzone);

        System.out.println(String.format("Joda-time: Time now in '%s' is '%s'", 
            dateTime.getZone(), dateTime.toString(PATTERN)));

        return dateTime.getZone();
    }

}
