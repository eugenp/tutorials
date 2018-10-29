package com.baeldung.kotlin.dates

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun createDateUsingParseMethodDefaultFormat() {

    var date = LocalDate.parse("2018-12-31")
    println(date)
}

fun createDateUsingParseMethodCustomFormat() {

    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    var date = LocalDate.parse("31-12-2018", formatter)

    println(date)
}

fun createDateUsingOfMethod() {
    var date = LocalDate.of(2018, 12, 31)
    println(date)

}

fun main(args: Array<String>) {

    createDateUsingParseMethodCustomFormat()
    createDateUsingOfMethod()
}