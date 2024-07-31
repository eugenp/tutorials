package com.baeldung.epochconversion;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class EpochToLocalDate {

    public static void main(String[] args) {
        long millisSinceEpoch = 2131242L;
        ZoneId zoneId = ZoneId.of("Europe/Amsterdam"); // or: ZoneId.systemDefault();
        // to get All Zone Ids available, we can use the function below
        // Set<String> allZoneIds =  ZoneId.getAvailableZoneIds();
        LocalDate date =
            Instant.ofEpochMilli(millisSinceEpoch)
                .atZone(zoneId)
                .toLocalDate();
        LocalDateTime time =
            Instant.ofEpochMilli(millisSinceEpoch)
                .atZone(zoneId)
                .toLocalDateTime();
    }
}