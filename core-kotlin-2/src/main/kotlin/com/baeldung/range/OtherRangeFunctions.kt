package com.baeldung.range

fun main(args: Array<String>) {

    val r = 1..20
    println(r.min())
    println(r.max())
    println(r.sum())
    println(r.average())
    println(r.count())

    val repeated = listOf(1, 1, 2, 4, 4, 6, 10)
    println(repeated.distinct())
}