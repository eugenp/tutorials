package com.baeldung.kotlin.datetime

import com.baeldung.dates.datetime.UseLocalTime
import org.junit.Assert
import org.junit.Test
import java.time.LocalTime

class UseLocalTimeUnitTest {

    internal var useLocalTime = UseLocalTime()

    @Test
    fun givenValues_whenUsingFactoryOf_thenLocalTime() {
        Assert.assertEquals("07:07:07", useLocalTime.getLocalTimeUsingFactoryOfMethod(7, 7, 7).toString())
    }

    @Test
    fun givenString_whenUsingParse_thenLocalTime() {
        Assert.assertEquals("06:30", useLocalTime.getLocalTimeUsingParseMethod("06:30").toString())
    }

    @Test
    fun givenTime_whenAddHour_thenLocalTime() {
        Assert.assertEquals("07:30", useLocalTime.addAnHour(LocalTime.of(6, 30)).toString())
    }

    @Test
    fun getHourFromLocalTime() {
        Assert.assertEquals(1, useLocalTime.getHourFromLocalTime(LocalTime.of(1, 1)).toLong())
    }

    @Test
    fun getLocalTimeWithMinuteSetToValue() {
        Assert.assertEquals(LocalTime.of(10, 20), useLocalTime.getLocalTimeWithMinuteSetToValue(LocalTime.of(10, 10), 20))
    }
}