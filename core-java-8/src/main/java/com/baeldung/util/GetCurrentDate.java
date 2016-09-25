package com.baeldung.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GetCurrentDate {

    public static void main(String args[]) {

        LocalDate localDate = LocalDate.now();
        System.out.println("Today's date is: " + localDate);
        
        localDate = LocalDate.now(ZoneId.of("GMT+02:30"));
        System.out.println("Current date in GMT +02:30 timezone: " + localDate);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("Today's date is: " + localDateTime.toLocalDate());
    }
}
