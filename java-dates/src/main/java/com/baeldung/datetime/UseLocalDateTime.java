package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class UseLocalDateTime {

    public LocalDateTime getLocalDateTimeUsingParseMethod(String representation) {
        return LocalDateTime.parse(representation);
    }

    LocalDateTime getEndOfDayFromLocalDateTimeDirectly(LocalDateTime localDateTime) {
        LocalDateTime endOfDate = localDateTime.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
        return endOfDate;
    }

    LocalDateTime getEndOfDayFromLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime endOfDate = localDateTime.toLocalDate()
            .atTime(LocalTime.MAX);
        return endOfDate;
    }

}
