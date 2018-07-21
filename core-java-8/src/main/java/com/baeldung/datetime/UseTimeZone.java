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
        
        Date nowUtc = new Date();
        TimeZone asiaSingapore = TimeZone.getTimeZone(timeZone);
        
        Calendar nowAsiaSingapore = Calendar.getInstance(asiaSingapore);
        nowAsiaSingapore.setTime(nowUtc);
        
        System.out.println(String.format("Java7: Time now in '%s' is '%s'", 
            nowAsiaSingapore.getTimeZone().getID(), simpleDateFormat.format(nowAsiaSingapore.getTime())));

        return nowAsiaSingapore.getTimeZone();
    }

    public static ZoneId setTimeZoneUsingJava8(String timeZone) {
        Instant nowUtc = Instant.now();
        ZoneId asiaSingapore = ZoneId.of(timeZone);
        
        ZonedDateTime nowAsiaSingapore = ZonedDateTime.ofInstant(nowUtc, asiaSingapore);

        System.out.println(String.format("Java8: Time now in '%s' is '%s'", 
            nowAsiaSingapore.getZone(), nowAsiaSingapore.format(DateTimeFormatter.ofPattern(PATTERN))));

        return nowAsiaSingapore.getZone();
    }

    public static DateTimeZone setTimeZoneUsingJodaTime(String timeZone) {
        DateTimeZone tzone = DateTimeZone.forID(timeZone);
        DateTime dateTime = new DateTime(tzone);

        System.out.println(String.format("Joda-time: Time now in '%s' is '%s'", 
            dateTime.getZone(), dateTime.toString(PATTERN)));

        return dateTime.getZone();
    }

}
