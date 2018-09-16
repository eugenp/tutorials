package com.baeldung.kotlin.arrays

fun arraysWithFunctionToValues(size: Int): Array<Int> {
    return Array(size, { i -> i * 10} )
}

fun arraysWithArrayOf(element1: Int, element2: Int, element3: Int): Array<Int> {
    return arrayOf(element1, element2, element3)
}

fun arraysWithArrayOfNull(size: Int): Array<Int?> {
    return arrayOfNulls<Int>(size)
}