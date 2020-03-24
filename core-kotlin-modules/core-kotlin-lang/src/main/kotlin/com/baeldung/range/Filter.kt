package com.baeldung.range

fun main(args: Array<String>) {
    val r = 1..10

    //Apply filter
    val f = r.filter { it -> it % 2 == 0 }
    println(f)

    //Map
    val m = r.map { it -> it * it }
    println(m)

    //Reduce
    val rdc = r.reduce { a, b -> a + b }
    println(rdc)

}