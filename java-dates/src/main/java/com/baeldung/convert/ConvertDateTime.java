package com.baeldung.convert;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class ConvertDateTime {

    public static void main(String[] args) throws ParseException {

        java8();
        joda();
        Date date = simpleDateTimeFormatter();
        calendar(date);
    }

    private static void calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
    }

    private static Date simpleDateTimeFormatter() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "22-01-2015 10:20:56";
        Date date = sdf.parse(dateInString);

        System.out.println(dateInString);
        System.out.println("Date - Time in milliseconds : " + date.getTime());

        return date;
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
