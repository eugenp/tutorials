package com.baeldung.lambda

fun inferredType(input: Int): Int {
    val square = { number: Int -> number * number }

    return square(input)
}

fun intToBiggerString(argument: Int): String {

    val magnitude100String = { input: Int ->
        val magnitude = input * 100
        magnitude.toString()
    }

    return magnitude100String(argument)
}

fun manyLambda(nums: Array<Int>): List<String> {
    val newList = nums.map { intToBiggerString(it) }

    return newList
}

fun empty() {
    val noReturn: (Int) -> Unit = { num -> println(num) }

    noReturn(5)
}

fun invokeLambda(lambda: (Double) -> Boolean): Boolean {
    return lambda(4.329)
}

fun extendString(arg: String, num: Int): String {
    val another: String.(Int) -> String = { this + it }

    return arg.another(num)
}