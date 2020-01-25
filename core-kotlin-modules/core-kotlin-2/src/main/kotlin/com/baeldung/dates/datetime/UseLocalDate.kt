package com.baeldung.dates.datetime

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

class UseLocalDate {

    fun getLocalDateUsingFactoryOfMethod(year: Int, month: Int, dayOfMonth: Int): LocalDate {
        return LocalDate.of(year, month, dayOfMonth)
    }

    fun getLocalDateUsingParseMethod(representation: String): LocalDate {
        return LocalDate.parse(representation)
    }

    fun getLocalDateFromClock(): LocalDate {
        return LocalDate.now()
    }

    fun getNextDay(localDate: LocalDate): LocalDate {
        return localDate.plusDays(1)
    }

    fun getPreviousDay(localDate: LocalDate): LocalDate {
        return localDate.minus(1, ChronoUnit.DAYS)
    }

    fun getDayOfWeek(localDate: LocalDate): DayOfWeek {
        return localDate.dayOfWeek
    }

    fun getFirstDayOfMonth(): LocalDate {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
    }

    fun getStartOfDay(localDate: LocalDate): LocalDateTime {
        return localDate.atStartOfDay()
    }
}