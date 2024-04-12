package com.baeldung.epochtolocaldate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Class which shows a way to convert Epoch time in milliseconds to java.time.LocalDate.
 * 
 * @author quincy
 *
 */

public class EpochTimeToLocalDateConverter {
    public static String main(String[] args) {
        long epochTimeinMillisToConvert = 1624962431000L;
        
        Instant instant = Instant.ofEpochMilli(epochTimeinMillisToConvert);
        
        ZoneId zoneId = ZoneId.systemDefault(); // Use the system default time zone
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = localDate.format(formatter);
        
        return formattedDate;
    }
}
