package com.baeldung.range

fun main(args: Array<String>) {

    (1..9).reversed().forEach {
        print(it)
    }

    println()

    (1..9).reversed().step(3).forEach {
        print(it)
    }
}