package com.baeldung.ifelseexpression

fun ifStatementUsage(): String {
    val number = 15

    if (number > 0) {
        return "Positive number"
    }
    return "Positive number not found"
}

fun ifElseStatementUsage(): String {
    val number = -50

    if (number > 0) {
        return "Positive number"
    } else {
        return "Negative number"
    }
}

fun ifElseExpressionUsage(): String {
    val number = -50

    val result = if (number > 0) {
        "Positive number"
    } else {
        "Negative number"
    }
    return result
}

fun ifElseExpressionSingleLineUsage(): String {
    val number = -50
    val result = if (number > 0) "Positive number" else "Negative number"

    return result
}

fun ifElseMultipleExpressionUsage(): Int {
    val x = 24
    val y = 73

    val result = if (x > y) {
        println("$x is greater than $y")
        x
    } else {
        println("$x is less than or equal to $y")
        y
    }
    return result
}

fun ifElseLadderExpressionUsage(): String {
    val number = 60

    val result = if (number < 0) {
        "Negative number"
    } else if (number in 0..9) {
        "Single digit number"
    } else if (number in 10..99) {
        "Double digit number"
    } else {
        "Number has more digits"
    }
    return result
}

fun ifElseNestedExpressionUsage(): Int {
    val x = 37
    val y = 89
    val z = 6

    val result = if (x > y) {
        if (x > z)
            x
        else
            z
    } else {
        if (y > z)
            y
        else
            z
    }
    return result
}