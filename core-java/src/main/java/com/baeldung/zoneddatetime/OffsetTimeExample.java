package com.baeldung.zoneddatetime;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetTimeExample {

    public OffsetTime getCurrentTimeByZoneOffset(String region) {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of(region);
        ZoneOffset zoneOffSet = zone.getRules()
            .getOffset(now);
        OffsetTime time = OffsetTime.now(zoneOffSet);
        return time;
    }
}
