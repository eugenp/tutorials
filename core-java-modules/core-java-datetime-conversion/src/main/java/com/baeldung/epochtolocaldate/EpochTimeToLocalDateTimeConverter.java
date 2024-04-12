package com.baeldung.epochtolocaldate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Class which shows a way to convert Epoch time in milliseconds to java.time.LocalDateTime.
 * 
 * @author quincy
 *
 */

public class EpochTimeToLocalDateTimeConverter {
    public static String main(String[] args) {
        long epochTimeinMillisToConvert = 1624962431000L;

        Instant instant = Instant.ofEpochMilli(epochTimeinMillisToConvert);
        
        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDateTime.format(formatter);
        
        return formattedDate;
    }
}
