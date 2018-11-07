package com.baeldung.scala

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

import Utils.average
import Utils.fibonacci
import Utils.power
import Utils.randomLessThan

class UtilsUnitTest {

  @Test
  def whenAverageCalled_thenCorrectValueReturned() = {
    assertEquals(15.0, average(10, 20), 1e-5)
  }

  @Test
  def whenRandomLessThanInvokedWithANumber_thenARandomLessThanItReturned = {
    val d = 0.1
    assertTrue(randomLessThan(d) < d)
  }

  @Test
  def whenPowerInvokedWith2And3_then8Returned = {
    assertEquals(8, power(2, 3))
  }

  @Test
  def whenFibonacciCalled_thenCorrectValueReturned = {
    assertEquals(1, fibonacci(0))
    assertEquals(1, fibonacci(1))
    assertEquals(fibonacci(6),
      fibonacci(4) + fibonacci(5))
  }
}