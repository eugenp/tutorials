@file:JvmName("Lists")
package com.baeldung.kotlin

fun <T> MutableList<T>.swap(firstIndex: Int, secondIndex: Int): MutableList<T> {
    val tmp = this[firstIndex]
    this[firstIndex] = this[secondIndex]
    this[secondIndex] = tmp
    return this
}
