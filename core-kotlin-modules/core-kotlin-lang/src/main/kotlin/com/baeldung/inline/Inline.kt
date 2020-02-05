package com.baeldung.inline

import kotlin.random.Random

/**
 * An extension function on all collections to apply a function to all collection
 * elements.
 */
fun <T> Collection<T>.each(block: (T) -> Unit) {
    for (e in this) block(e)
}

/**
 * In order to see the the JVM bytecode:
 *  1. Compile the Kotlin file using `kotlinc Inline.kt`
 *  2. Take a peek at the bytecode using the `javap -c InlineKt`
 */
fun main() {
    val numbers = listOf(1, 2, 3, 4, 5)
    val random = random()

    numbers.each { println(random * it) } // capturing the random variable
}

/**
 * Generates a random number.
 */
private fun random(): Int = Random.nextInt()