package com.baeldung.epochconversion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateToEpoch {

    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Europe/Amsterdam"); // or: ZoneId.systemDefault()
        LocalDate date = LocalDate.now();
        long epochMilliSecondsAtDate = date.atStartOfDay(zoneId).toInstant().toEpochMilli();

        // epoch for time
        LocalDateTime time = LocalDateTime.parse("2019-11-15T13:15:30");
        long epochMilliSecondsAtTime = time.atZone(zoneId).toInstant().toEpochMilli();
    }
}