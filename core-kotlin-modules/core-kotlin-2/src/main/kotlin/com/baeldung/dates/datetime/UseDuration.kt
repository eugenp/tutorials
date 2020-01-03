package com.baeldung.dates.datetime

import java.time.Duration
import java.time.LocalTime

class UseDuration {

    fun modifyDates(localTime: LocalTime, duration: Duration): LocalTime {
        return localTime.plus(duration)
    }

    fun getDifferenceBetweenDates(localTime1: LocalTime, localTime2: LocalTime): Duration {
        return Duration.between(localTime1, localTime2)
    }
}