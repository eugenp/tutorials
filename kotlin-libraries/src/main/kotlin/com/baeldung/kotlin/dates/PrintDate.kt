package com.baeldung.kotlin.dates

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun printDateDefaultFormat() {

    var date = LocalDate.parse("2018-12-31")
    println(date)
}

fun printDateUsingCustomFormat() {

    var date = LocalDate.parse("2018-12-31")

    var formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    var formattedDate = date.format(formatter)
    println(formattedDate)
}

fun main(args: Array<String>) {

    printDateDefaultFormat()

    printDateUsingCustomFormat()
}