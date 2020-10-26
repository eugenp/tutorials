package com.baeldung.dates.datetime

import java.time.LocalTime
import java.time.temporal.ChronoUnit

class UseLocalTime {

    fun getLocalTimeUsingFactoryOfMethod(hour: Int, min: Int, seconds: Int): LocalTime {
        return LocalTime.of(hour, min, seconds)
    }

    fun getLocalTimeUsingParseMethod(timeRepresentation: String): LocalTime {
        return LocalTime.parse(timeRepresentation)
    }

    fun getLocalTimeFromClock(): LocalTime {
        return LocalTime.now()
    }

    fun addAnHour(localTime: LocalTime): LocalTime {
        return localTime.plus(1, ChronoUnit.HOURS)
    }

    fun getHourFromLocalTime(localTime: LocalTime): Int {
        return localTime.hour
    }

    fun getLocalTimeWithMinuteSetToValue(localTime: LocalTime, minute: Int): LocalTime {
        return localTime.withMinute(minute)
    }
}