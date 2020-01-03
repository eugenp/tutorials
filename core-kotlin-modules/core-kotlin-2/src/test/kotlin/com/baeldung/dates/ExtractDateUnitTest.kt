package com.baeldung.kotlin.dates

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

class ExtractDateUnitTest {

    @Test
    fun givenDate_thenExtractedYMD() {
        var date = LocalDate.parse("2018-12-31")

        assertThat(date.year).isEqualTo(2018)
        assertThat(date.month).isEqualTo(Month.DECEMBER)
        assertThat(date.dayOfMonth).isEqualTo(31)
    }

    @Test
    fun givenDate_thenExtractedEraDowDoy() {
        var date = LocalDate.parse("2018-12-31")

        assertThat(date.era.toString()).isEqualTo("CE")
        assertThat(date.dayOfWeek).isEqualTo(DayOfWeek.MONDAY)
        assertThat(date.dayOfYear).isEqualTo(365)
    }

}