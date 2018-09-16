package com.baeldung.zoneddatetime;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.junit.Test;

public class OffsetDateTimeExampleUnitTest {

    OffsetDateTimeExample offsetDateTimeExample = new OffsetDateTimeExample();

    @Test
    public void givenZoneOffset_whenGetCurrentTime_thenResultHasZone() {
        String zone = "Europe/Berlin";
        OffsetDateTime time = offsetDateTimeExample.getCurrentTimeByZoneOffset(zone);   

        assertTrue(time.getOffset()
            .equals(ZoneId.of(zone)
                .getRules()
                .getOffset(LocalDateTime.now())));
    }
}
