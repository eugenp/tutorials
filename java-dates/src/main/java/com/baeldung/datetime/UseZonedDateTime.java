package com.baeldung.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

class UseZonedDateTime {

    ZonedDateTime getZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    ZonedDateTime getStartOfDay(LocalDate localDate, ZoneId zone) {
        ZonedDateTime startofDay = localDate.atStartOfDay()
            .atZone(zone);
        return startofDay;
    }

    ZonedDateTime getStartOfDayShorthand(LocalDate localDate, ZoneId zone) {
        ZonedDateTime startofDay = localDate.atStartOfDay(zone);
        return startofDay;
    }

    ZonedDateTime getStartOfDayFromZonedDateTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startofDay = zonedDateTime.toLocalDateTime()
            .toLocalDate()
            .atStartOfDay(zonedDateTime.getZone());
        return startofDay;
    }

    ZonedDateTime getStartOfDayAtMinTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startofDay = zonedDateTime.with(ChronoField.HOUR_OF_DAY, 0);
        return startofDay;
    }

    ZonedDateTime getStartOfDayAtMidnightTime(ZonedDateTime zonedDateTime) {
        ZonedDateTime startofDay = zonedDateTime.with(ChronoField.NANO_OF_DAY, 0);
        return startofDay;
    }
}
