package com.baeldung.kotlin.datetime

import com.baeldung.dates.datetime.UsePeriod
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.Period

class UsePeriodUnitTest {

    var usingPeriod = UsePeriod()

    @Test
    fun givenPeriodAndLocalDate_thenCalculateModifiedDate() {
        val period = Period.ofDays(1)
        val localDate = LocalDate.parse("2007-05-10")
        Assert.assertEquals(localDate.plusDays(1), usingPeriod.modifyDates(localDate, period))
    }

    @Test
    fun givenDates_thenGetPeriod() {
        val localDate1 = LocalDate.parse("2007-05-10")
        val localDate2 = LocalDate.parse("2007-05-15")

        Assert.assertEquals(Period.ofDays(5), usingPeriod.getDifferenceBetweenDates(localDate1, localDate2))
    }
}