package com.baeldung.temporaladjuster;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class CustomTemporalAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        switch (DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK))) {
        case FRIDAY:
            return temporal.plus(3, ChronoUnit.DAYS);
        case SATURDAY:
            return temporal.plus(2, ChronoUnit.DAYS);
        default:
            return temporal.plus(1, ChronoUnit.DAYS);
        }
    }
}
