package com.baeldung.kotlin.dates

import java.time.LocalDate
import java.time.Period

fun createAPeriod() {
    var period = Period.of(1, 2, 3)

    println(period)
}

fun addAPeriod() {
    var period = Period.of(1, 2, 3)

    var date = LocalDate.of(2018, 6, 25)
    var modifiedDate = date.plus(period)

    println(modifiedDate)
}

fun subtractAPeriod() {
    var period = Period.of(1, 2, 3)

    var date = LocalDate.of(2018, 6, 25)
    var modifiedDate = date.minus(period)

    println(modifiedDate)
}

fun getAPeriod() {

    var date1 = LocalDate.parse("2018-06-25")
    var date2 = LocalDate.parse("2018-12-25")

    var period = Period.between(date1, date2)
    println(period)
}

fun main(args: Array<String>) {
    createAPeriod()
    addAPeriod()
    subtractAPeriod()
    getAPeriod()
}