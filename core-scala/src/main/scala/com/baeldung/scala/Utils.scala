package com.baeldung.scala

object Utils {
    def average(x: Double, y: Double) = (x + y) / 2

    def gcd(x: Int, y: Int): Int = {
        if (y == 0) x else gcd(y, x % y)
    }

    def gcdIter(x: Int, y: Int): Int = {
        var a = x
        var b = y
        while (b > 0) {
            a = a % b
            val t = a
            a = b
            b = t
        }
        return a
    }

    def rangeSum(a: Int, b: Int) = {
        var sum = 0;
        for (i <- a to b) {
            sum += i
        }
        sum
    }

    def factorial(a: Int): Int = {
        var result = 1;
        var i = a;
        do {
            result *= i
            i = i - 1
        } while (i > 0)
        result
    }

    def randomLessThan(d: Double) = {
        var random = 0d
        do {
            random = Math.random()
        } while (random >= d)
        random
    }

    def whileLoop(condition: => Boolean)(body: => Unit): Unit =
        if (condition) {
            body
            whileLoop(condition)(body)
        }

    def power(x: Int, y: Int): Int = {
        def powNested(i: Int, accumulator: Int): Int = {
            if (i <= 0) accumulator
            else powNested(i - 1, x * accumulator)
        }
        powNested(y, 1)
    }

    def fibonacci(n: Int): Int = n match {
        case 0 | 1      => 1
        case x if x > 1 => fibonacci(x - 1) + fibonacci(x - 2)
    }
}