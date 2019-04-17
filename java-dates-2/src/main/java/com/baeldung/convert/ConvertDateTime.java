package com.baeldung.convert;

import org.joda.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class ConvertDateTime {

    public static long coreDate() {
        Date date = new Date();
        return date.getTime();
    }

    public static long calendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }

    public static long jodaInstant() {
        Instant jodaInstant = Instant.now();
        return jodaInstant.getMillis();
    }

    public static long jodaDateTime() {
        org.joda.time.DateTime jodaDateTime = new org.joda.time.DateTime();
        return jodaDateTime.getMillis();
    }

    public static long java8Instant() {
        return java.time.Instant.now().toEpochMilli();
    }

    public static long java8LocalDateTime() {

        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId id = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, id);
        return zdt.toInstant().toEpochMilli();
    }
}
