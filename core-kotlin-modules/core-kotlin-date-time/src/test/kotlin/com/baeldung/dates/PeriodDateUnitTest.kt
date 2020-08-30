package com.baeldung.kotlin.dates

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Period

class PeriodDateUnitTest {

    @Test
    fun givenYMD_thenCreatePeriod() {
        var period = Period.of(1, 2, 3)

        assertThat(period.toString()).isEqualTo("P1Y2M3D")
    }

    @Test
    fun givenPeriod_whenAdd_thenModifiedDate() {
        var period = Period.of(1, 2, 3)

        var date = LocalDate.of(2018, 6, 25)
        var modifiedDate = date.plus(period)

        assertThat(modifiedDate).isEqualTo("2019-08-28")
    }

    @Test
    fun givenPeriod_whenSubtracted_thenModifiedDate() {
        var period = Period.of(1, 2, 3)

        var date = LocalDate.of(2018, 6, 25)
        var modifiedDate = date.minus(period)

        assertThat(modifiedDate).isEqualTo("2017-04-22")
    }

    @Test
    fun givenTwoDate_whenUsingBetween_thenDiffOfDates() {

        var date1 = LocalDate.parse("2018-06-25")
        var date2 = LocalDate.parse("2018-12-25")

        var period = Period.between(date1, date2)

        assertThat(period.toString()).isEqualTo("P6M")
    }

}