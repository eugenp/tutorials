package com.baeldung.examples

object LoopAndCalculationExamples extends App {
    import CalculationUtils._
    println(sumWithWhile(Array(10, 20, 30)))
    println(sumWithFor(Array(10, 20, 30)))
    println(checkEvenOdd((1 to 10).toArray).toList)
    println(filterEvens((1 to 10).toArray).toList)
}

object CalculationUtils {

    def sumWithWhile(values:Array[Int]) = {
        var sum = 0
        var i = 0
        while ( i < values.length) {
            sum += values(i)
            i+=1
        }
        sum
    }

    def sumWithFor(values:Array[Int]) = {
        var sum = 0
        for (i <- values) {
            sum += i
        }
        sum
    }

    def checkEvenOdd(values:Array[Int]) = {
        for (i <- values) yield if (i%2 == 0) "Even" else "Odd"
    }

    def filterEvens(values:Array[Int]) =
        for (i <- values if i%2 == 0)
            yield i

}
