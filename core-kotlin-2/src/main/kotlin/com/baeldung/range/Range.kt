package com.baeldung.range

fun main(args: Array<String>) {

    for (i in 1..9) {
        print(i)
    }
    println()

    for (i in 9 downTo 1) {
        print(i)
    }
    println()

    for (i in 1.rangeTo(9)) {
        print(i)
    }
    println()

    for (i in 9.downTo(1)) {
        print(i)
    }
    println()

    for (i in 1 until 9) {
        print(i)
    }
}
