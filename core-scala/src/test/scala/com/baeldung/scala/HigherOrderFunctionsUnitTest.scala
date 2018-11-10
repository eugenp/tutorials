package com.baeldung.scala

import com.baeldung.scala.HigherOrderFunctions.mapReduce
import org.junit.Assert.assertEquals
import org.junit.Test

class HigherOrderFunctionsUnitTest {

  @Test
  def whenCalledWithSumAndSquareFunctions_thenCorrectValueReturned() = {
    def square(x : Int) = x * x

    def sum(x : Int, y : Int) = x + y

    def sumSquares(a : Int, b : Int) =
      mapReduce(sum, 0, square, a, b)

    assertEquals(385, sumSquares(1, 10))
  }

  @Test
  def whenComputingSumOfSquaresWithAnonymousFunctions_thenCorrectValueReturned() = {
    def sumSquares(a : Int, b : Int) =
      mapReduce((x, y) => x + y, 0, x => x * x, a, b)

    assertEquals(385, sumSquares(1, 10))
  }

  @Test
  def givenCurriedFunctions_whenInvoked_thenCorrectValueReturned() = {
    // a curried function
    def sum(f : Int => Int)(a : Int,
                            b : Int) : Int =
      if (a > b) 0 else f(a) + sum(f)(a + 1, b)

    // another curried function
    def mod(n : Int)(x : Int) = x % n

    // application of a curried function
    assertEquals(1, mod(5)(6))

    // partial application of curried function
    // trailing underscore is required to make function type explicit
    val sumMod5 = sum(mod(5)) _

    assertEquals(10, sumMod5(6, 10))
  }
}