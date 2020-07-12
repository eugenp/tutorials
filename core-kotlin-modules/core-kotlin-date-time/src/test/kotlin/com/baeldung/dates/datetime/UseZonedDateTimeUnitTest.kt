package com.baeldung.kotlin.datetime

import com.baeldung.dates.datetime.UseZonedDateTime
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class UseZonedDateTimeUnitTest {

    internal var zonedDateTime = UseZonedDateTime()

    @Test
    fun givenZoneId_thenZonedDateTime() {
        val zoneId = ZoneId.of("Europe/Paris")
        val zonedDatetime = zonedDateTime.getZonedDateTime(LocalDateTime.parse("2016-05-20T06:30"), zoneId)
        Assert.assertEquals(zoneId, ZoneId.from(zonedDatetime))
    }
}