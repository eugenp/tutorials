package com.baeldung.index

fun main() {

    // Index only
    val colors = listOf("Red", "Green", "Blue")
    for (i in colors.indices) {
        println(colors[i])
    }

    val colorArray = arrayOf("Red", "Green", "Blue")
    for (i in colorArray.indices) {
        println(colorArray[i])
    }

    (0 until colors.size).forEach { println(colors[it]) }
    for (i in 0 until colors.size) {
        println(colors[i])
    }

    // Index and Value
    colors.forEachIndexed { i, v -> println("The value for index $i is $v") }
    for (indexedValue in colors.withIndex()) {
        println("The value for index ${indexedValue.index} is ${indexedValue.value}")
    }

    for ((i, v) in colors.withIndex()) {
        println("The value for index $i is $v")
    }

    colors.filterIndexed { i, _ -> i % 2 == 0 }
    colors.filterIndexed { _, v -> v == "RED" }
}
