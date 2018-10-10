package com.baeldung.scala

import org.junit.Test
import Utils._
import org.junit.Assert._

class UtilsUnitTest {

    @Test
    def whenAverageCalled_thenCorrectValueReturned() = {
        val average = Utils.average(10, 20)
        assertEquals(15.0, average, 1e-5)
    }

    @Test
    def givenTwoIntegers_whenGcdCalled_thenCorrectValueReturned = {
        assertEquals(3, Utils.gcd(15, 27))
    }

    @Test
    def givenTwoIntegers_whenGcdIterCalled_thenCorrectValueReturned = {
        assertEquals(3, Utils.gcdIter(15, 27))
    }

    @Test
    def givenTwoIntegers_whenRangeSumcalled_thenCorrectValueReturned = {
        assertEquals(55, Utils.rangeSum(1, 10))
    }

    @Test
    def givenPositiveInteger_whenFactorialInvoked_thenCorrectValueReturned = {
        assertEquals(720, Utils.factorial(6))
    }

    @Test
    def whenRandomLessThanInvokedWithANumber_thenARandomLessThanItReturned = {
        val d = 0.1
        assertTrue(Utils.randomLessThan(d) < d)
    }

    @Test
    def whenPowerInvokedWith2And3_then8Returned = {
        assertEquals(8, power(2, 3))
    }

    @Test
    def whenFibonacciCalled_thenCorrectValueReturned = {
        assertEquals(34, fibonacci(8))
    }
}