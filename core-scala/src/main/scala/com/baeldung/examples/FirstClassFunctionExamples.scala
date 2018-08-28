package com.baeldung.examples

object FirstClassFunctionExamples extends App {

    def sum(x:Double, y:Double, f:Double => Double) = f(x) + f(y)

    println(sum(3, 4, x => x*x)) // returns 25.0

    println(sum(3, 4, x => x*x*x)) // returns 91.0

    println(sum(3, 4, Math.pow(_, 2))) // returns 25.0

    def max(first:Int, second:Int) = if (first > second) first else second

    println(max(10, -2))

}
