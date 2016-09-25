package com.baeldung.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class GetCurrentTime {

    public static void main(String args[]) {
        
        LocalTime localTime = LocalTime.now();
        System.out.println("Current time is: " + localTime);
        
        localTime = LocalTime.now(ZoneId.of("GMT+02:30"));
        System.out.println("Current time in GMT +02:30 timezone: " + localTime);
        
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("Current time is: " + localDateTime.toLocalTime());
    }
}
