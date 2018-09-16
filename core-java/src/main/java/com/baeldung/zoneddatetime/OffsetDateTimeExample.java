package com.baeldung.zoneddatetime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeExample {

    public OffsetDateTime getCurrentTimeByZoneOffset(String region) {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of(region);
        ZoneOffset zoneOffSet= zone.getRules().getOffset(now);
        OffsetDateTime date = OffsetDateTime.now(zoneOffSet);
        return date;
    }
}
