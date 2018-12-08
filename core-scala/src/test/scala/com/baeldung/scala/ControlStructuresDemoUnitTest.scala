package com.baeldung.scala

import com.baeldung.scala.ControlStructuresDemo._
import org.junit.Assert.assertEquals
import org.junit.Test

class ControlStructuresDemoUnitTest {
  @Test
  def givenTwoIntegers_whenGcdCalled_thenCorrectValueReturned() = {
    assertEquals(3, gcd(15, 27))
  }

  @Test
  def givenTwoIntegers_whenGcdIterCalled_thenCorrectValueReturned() = {
    assertEquals(3, gcdIter(15, 27))
  }

  @Test
  def givenTwoIntegers_whenRangeSumcalled_thenCorrectValueReturned() = {
    assertEquals(55, rangeSum(1, 10))
  }

  @Test
  def givenPositiveInteger_whenFactorialInvoked_thenCorrectValueReturned() = {
    assertEquals(720, factorial(6))
  }

  @Test
  def whenFactorialOf0Invoked_then1Returned() = {
    assertEquals(1, factorial(0))
  }

}