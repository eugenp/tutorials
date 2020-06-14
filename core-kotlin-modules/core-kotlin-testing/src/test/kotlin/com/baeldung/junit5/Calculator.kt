package com.baeldung.junit5

class Calculator {
    fun add(a: Int, b: Int) = a + b

    fun divide(a: Int, b: Int) = if (b == 0) {
        throw DivideByZeroException(a)
    } else {
        a / b
    }

    fun square(a: Int) = a * a

    fun squareRoot(a: Int) = Math.sqrt(a.toDouble())

    fun log(base: Int, value: Int) = Math.log(value.toDouble()) / Math.log(base.toDouble())
}
