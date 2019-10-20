package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Test
    public void givenLocalDateOrZoned_whenSettingStartOfDay_thenReturnMidnightInAllCases() {
        LocalDate given = LocalDate.parse("2018-06-23");
        ZoneId zone = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedGiven = ZonedDateTime.of(given, LocalTime.NOON, zone);

        ZonedDateTime startOfOfDayWithMethod = zonedDateTime.getStartOfDay(given, zone);
        ZonedDateTime startOfOfDayWithShorthandMethod = zonedDateTime.getStartOfDayShorthand(given, zone);
        ZonedDateTime startOfOfDayFromZonedDateTime = zonedDateTime.getStartOfDayFromZonedDateTime(zonedGiven);
        ZonedDateTime startOfOfDayAtMinTime = zonedDateTime.getStartOfDayAtMinTime(zonedGiven);
        ZonedDateTime startOfOfDayAtMidnightTime = zonedDateTime.getStartOfDayAtMidnightTime(zonedGiven);

        assertThat(startOfOfDayWithMethod).isEqualTo(startOfOfDayWithShorthandMethod)
            .isEqualTo(startOfOfDayFromZonedDateTime)
            .isEqualTo(startOfOfDayAtMinTime)
            .isEqualTo(startOfOfDayAtMidnightTime);
        assertThat(startOfOfDayWithMethod.toLocalTime()).isEqualTo(LocalTime.MIDNIGHT);
        assertThat(startOfOfDayWithMethod.toLocalTime()
            .toString()).isEqualTo("00:00");
    }
}
