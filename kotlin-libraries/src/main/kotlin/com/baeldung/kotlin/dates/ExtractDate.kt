package com.baeldung.kotlin.dates

import java.time.LocalDate

fun extractingCommonComponents() {
    var date = LocalDate.parse("2018-12-31")

    println(date.year)
    println(date.month)
    println(date.dayOfMonth)
}

fun extractingEraDowDoy() {
    var date = LocalDate.parse("2018-12-31")

    println(date.era)
    println(date.dayOfWeek)
    println(date.dayOfYear)
}

fun main(args: Array<String>) {
    extractingCommonComponents()
    extractingEraDowDoy()
}