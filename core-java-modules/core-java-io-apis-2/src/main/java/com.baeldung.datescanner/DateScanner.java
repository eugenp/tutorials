package com.bealdung.datescanner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class DateScanner {

    public static void main(String[] args) {
        System.out.println(txtDate("Thursday 30 May 2019"));

        Scanner strResult = new Scanner(getLocalTime().toString());
        System.out.println(strResult.nextLine());
    }

    public static String txtDate(String strDate){
        Scanner strResult = new Scanner(strDate);
        String dateString = strResult.nextLine();
        LocalDate date = LocalDate.parse(dateString,
            DateTimeFormatter.ofPattern("EEEE dd MMM uuuu",  Locale.ENGLISH));
        return date.toString();
    }

    public static ZonedDateTime getZonedDateTime(){
        ZoneId zoneId = ZoneId.of("Africa/Kampala");
        return ZonedDateTime.of(getLocalDateTime(), zoneId);
    }

    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

    public static LocalTime getLocalTime(){
        return LocalTime.now();
    }

    public static DayOfWeek getDayOfWeek(){
        return LocalDate.parse(getLocalDate().toString()).getDayOfWeek();
    }

    public static LocalDate getLocalDate(){
        return LocalDate.now();
    }
}


