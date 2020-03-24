package com.baeldung.range

enum class Color(val rgb: Int) {
    BLUE(0x0000FF),
    GREEN(0x008000),
    RED(0xFF0000),
    MAGENTA(0xFF00FF),
    YELLOW(0xFFFF00);
}

fun main(args: Array<String>) {

    println(Color.values().toList());
    val red = Color.RED
    val yellow = Color.YELLOW
    val range = red..yellow

    println(range.contains(Color.MAGENTA))
    println(range.contains(Color.BLUE))
    println(range.contains(Color.GREEN))
}