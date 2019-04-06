package com.baeldung.range

fun main(args: Array<String>) {

    println((1..9).first)
    println((1..9 step 2).step)
    println((3..9).reversed().last)
}