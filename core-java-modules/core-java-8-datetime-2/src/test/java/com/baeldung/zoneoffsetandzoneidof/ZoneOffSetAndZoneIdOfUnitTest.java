package com.baeldung.zoneoffsetandzoneidof;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZoneOffSetAndZoneIdOfUnitTest {

    @Test
    public void givenOffsetDateTimeWithUTCZoneOffset_thenOffsetShouldBeUTC() {
        OffsetDateTime dateTimeWithOffset = OffsetDateTime.now(ZoneOffset.UTC);
        assertEquals(dateTimeWithOffset.getOffset(), ZoneOffset.UTC);
    }

    @Test
    public void givenZonedDateTimeWithUTCZoneId_thenZoneShouldBeUTC() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        assertEquals(zonedDateTime.getZone(), ZoneId.of("UTC"));
    }
}
