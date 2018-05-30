package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

public class UseZonedDateTimeUnitTest {

    UseZonedDateTime zonedDateTime = new UseZonedDateTime();

    @Test
    public void givenZoneId_thenZonedDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDatetime = zonedDateTime.getZonedDateTime(LocalDateTime.parse("2016-05-20T06:30"), zoneId);
        Assert.assertEquals(zoneId, ZoneId.from(zonedDatetime));
    }
}
