package com.baeldung.dates.datetime

import java.time.LocalDate
import java.time.Period

class UsePeriod {

    fun modifyDates(localDate: LocalDate, period: Period): LocalDate {
        return localDate.plus(period)
    }

    fun getDifferenceBetweenDates(localDate1: LocalDate, localDate2: LocalDate): Period {
        return Period.between(localDate1, localDate2)
    }
}