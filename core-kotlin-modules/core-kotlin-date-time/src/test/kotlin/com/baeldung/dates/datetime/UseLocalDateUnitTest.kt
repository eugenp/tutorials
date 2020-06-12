package com.baeldung.kotlin.datetime

import com.baeldung.dates.datetime.UseLocalDate
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

class UseLocalDateUnitTest {

    var useLocalDate = UseLocalDate()

    @Test
    fun givenValues_whenUsingFactoryOf_thenLocalDate() {
        Assert.assertEquals("2016-05-10", useLocalDate.getLocalDateUsingFactoryOfMethod(2016, 5, 10)
                .toString())
    }

    @Test
    fun givenString_whenUsingParse_thenLocalDate() {
        Assert.assertEquals("2016-05-10", useLocalDate.getLocalDateUsingParseMethod("2016-05-10")
                .toString())
    }

    @Test
    fun whenUsingClock_thenLocalDate() {
        Assert.assertEquals(LocalDate.now(), useLocalDate.getLocalDateFromClock())
    }

    @Test
    fun givenDate_whenUsingPlus_thenNextDay() {
        Assert.assertEquals(LocalDate.now()
                .plusDays(1), useLocalDate.getNextDay(LocalDate.now()))
    }

    @Test
    fun givenDate_whenUsingMinus_thenPreviousDay() {
        Assert.assertEquals(LocalDate.now()
                .minusDays(1), useLocalDate.getPreviousDay(LocalDate.now()))
    }

    @Test
    fun givenToday_whenUsingGetDayOfWeek_thenDayOfWeek() {
        Assert.assertEquals(DayOfWeek.SUNDAY, useLocalDate.getDayOfWeek(LocalDate.parse("2016-05-22")))
    }

    @Test
    fun givenToday_whenUsingWithTemporalAdjuster_thenFirstDayOfMonth() {
        Assert.assertEquals(1, useLocalDate.getFirstDayOfMonth()
                .dayOfMonth.toLong())
    }

    @Test
    fun givenLocalDate_whenUsingAtStartOfDay_thenReturnMidnight() {
        Assert.assertEquals(LocalDateTime.parse("2016-05-22T00:00:00"), useLocalDate.getStartOfDay(LocalDate.parse("2016-05-22")))
    }

}