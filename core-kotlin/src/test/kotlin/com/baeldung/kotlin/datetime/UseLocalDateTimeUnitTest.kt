package com.baeldung.kotlin.datetime

import com.baeldung.datetime.UseLocalDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

import org.junit.Test

import org.junit.Assert.assertEquals

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