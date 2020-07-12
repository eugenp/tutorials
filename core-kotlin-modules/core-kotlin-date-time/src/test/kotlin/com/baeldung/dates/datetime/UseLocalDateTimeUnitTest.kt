package com.baeldung.kotlin.datetime

import com.baeldung.dates.datetime.UseLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

class UseLocalDateTimeUnitTest {

    var useLocalDateTime = UseLocalDateTime()

    @Test
    fun givenString_whenUsingParse_thenLocalDateTime() {
        assertEquals(LocalDate.of(2016, Month.MAY, 10), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30")
                .toLocalDate())
        assertEquals(LocalTime.of(6, 30), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30")
                .toLocalTime())
    }
}