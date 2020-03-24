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

fun getCalculationLambda(): (Int) -> Any {
    val calculateGrade = { grade: Int ->
        when (grade) {
            in 0..40 -> "Fail"
            in 41..70 -> "Pass"
            in 71..100 -> "Distinction"
            else -> false
        }
    }

    return calculateGrade
}

fun getCalculationLambdaWithReturn(): (Int) -> String {
    val calculateGrade: Int.() -> String = lambda@{
        if (this < 0 || this > 100) {
            return@lambda "Error"
        } else if (this < 40) {
            return@lambda "Fail"
        } else if (this < 70) {
            return@lambda "Pass"
        }

        "Distinction"
    }

    return calculateGrade
}

fun getCalculationAnonymousFunction(): (Int) -> String {
    val calculateGrade = fun(grade: Int): String {
        if (grade < 0 || grade > 100) {
            return "Error"
        } else if (grade < 40) {
            return "Fail"
        } else if (grade < 70) {
            return "Pass"
        }

        return "Distinction"
    }

    return calculateGrade
}