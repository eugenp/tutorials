package com.baeldung.scala

import org.junit.Test
import org.junit.Assert._
import HigherOrderFunctions._

class HigherOrderFunctionsUnitTest {

    @Test
    def whenCalledWithSumAndSquareFunctions_thenCorrectValueReturned = {
        def square(x: Int) = x * x

        def sum(x: Int, y: Int) = x + y

        def sumSquares(a: Int, b: Int) =
            mapReduce(sum, 0, square, a, b)

        val n = 10
        val expected = n * (n + 1) * (2 * n + 1) / 6
        assertEquals(expected, sumSquares(1, n));
    }

    @Test
    def whenComputingSumOfSquaresWithAnonymousFunctions_thenCorrectValueReturned = {
        def sumSquares(a: Int, b: Int) =
            mapReduce((x, y) => x + y, 0, x => x * x, a, b)

        val n = 10
        val expected = n * (n + 1) * (2 * n + 1) / 6
        assertEquals(expected, sumSquares(1, n));
    }

    @Test
    def givenCurriedFunctions_whenInvoked_thenCorrectValueReturned = {
        def sum(f: Int => Int)(a: Int, b: Int): Int =
            if (a > b) 0 else f(a) + sum(f)(a + 1, b)

        def mod(n: Int)(x: Int) = x % n

        def sumMod5 = sum(mod(5)) _
        
        assertEquals(10, sumMod5(6,10));
    }
}