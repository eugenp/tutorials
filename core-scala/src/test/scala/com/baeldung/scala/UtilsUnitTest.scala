package com.baeldung.scala

import com.baeldung.scala.Utils.{average, fibonacci, power, randomLessThan}
import org.junit.Assert.{assertEquals, assertTrue}
import org.junit.Test

class UtilsUnitTest {

  @Test
  def whenAverageCalled_thenCorrectValueReturned(): Unit = {
    assertEquals(15.0, average(10, 20), 1e-5)
  }

  @Test
  def whenRandomLessThanInvokedWithANumber_thenARandomLessThanItReturned: Unit = {
    val d = 0.1
    assertTrue(randomLessThan(d) < d)
  }

  @Test
  def whenPowerInvokedWith2And3_then8Returned: Unit = {
    assertEquals(8, power(2, 3))
  }

  @Test
  def whenFibonacciCalled_thenCorrectValueReturned: Unit = {
    assertEquals(1, fibonacci(0))
    assertEquals(1, fibonacci(1))
    assertEquals(fibonacci(6),
      fibonacci(4) + fibonacci(5))
  }
}