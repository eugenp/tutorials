package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class UseZonedDateTimeUnitTest {

    private UseZonedDateTime zonedDateTime = new UseZonedDateTime();

    @Test
    public void givenZoneId_thenZonedDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDatetime = zonedDateTime.getZonedDateTime(LocalDateTime.parse("2016-05-20T06:30"), zoneId);
        Assert.assertEquals(zoneId, ZoneId.from(zonedDatetime));
    }

    @Test
    public void whenRequestingZones_thenAtLeastOneIsReturned() {
        Set<String> allZoneIds = ZoneId.getAvailableZoneIds();

        assertThat(allZoneIds.size()).isGreaterThan(1);
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

    @Test
    public void givenAStringWithTimeZone_whenParsing_thenEqualsExpected() {
        ZonedDateTime resultFromString = zonedDateTime.getZonedDateTimeUsingParseMethod("2015-05-03T10:15:30+01:00[Europe/Paris]");
        ZonedDateTime resultFromLocalDateTime = ZonedDateTime.of(2015, 5, 3, 10, 15, 30, 0, ZoneId.of("Europe/Paris"));

        assertThat(resultFromString.getZone()).isEqualTo(ZoneId.of("Europe/Paris"));
        assertThat(resultFromLocalDateTime.getZone()).isEqualTo(ZoneId.of("Europe/Paris"));

        assertThat(resultFromString).isEqualTo(resultFromLocalDateTime);
    }
}
