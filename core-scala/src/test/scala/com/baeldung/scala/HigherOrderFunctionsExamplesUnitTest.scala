package com.baeldung.scala

import org.junit.Assert.assertEquals
import org.junit.Test


class HigherOrderFunctionsExamplesUnitTest {

  @Test
  def whenCallingMapWithAnonymousFunction_thenTransformationIsApplied() = {
    val expected = Seq("sir Alex Ferguson", "sir Bobby Charlton", "sir Frank Lampard")

    val names = Seq("Alex Ferguson", "Bobby Charlton", "Frank Lampard")
    val sirNames = names.map(name => "sir " + name)

    assertEquals(expected, sirNames)
  }

  @Test
  def whenCallingMapWithDefined_thenTransformationIsApplied() = {
    val expected = Seq("sir Alex Ferguson", "sir Bobby Charlton", "sir Frank Lampard")

    val names = Seq("Alex Ferguson", "Bobby Charlton", "Frank Lampard")

    def prefixWithSir(name: String) = "sir " + name
    val sirNames = names.map(prefixWithSir)

    assertEquals(expected, sirNames)
  }

  @Test
  def whenCallingFilter_thenUnecessaryElementsAreRemoved() = {
    val expected = Seq("John O'Shea", "John Hartson")

    val names = Seq("John O'Shea", "Aiden McGeady", "John Hartson")
    val johns = names.filter(name => name.matches("^John .*"))

    assertEquals(expected, johns)
  }

  @Test
  def whenCallingReduce_thenProperSumIsCalculated() = {
    val expected = 2750

    val earnings = Seq(1000, 1300, 450)
    val sumEarnings = earnings.reduce((acc, x) => acc + x)

    assertEquals(expected, sumEarnings)
  }

  @Test
  def whenCallingFold_thenNumberOfWordsShouldBeCalculated() = {
    val expected = 6

    val strings = Seq("bunch of words", "just me", "it")
    val sumEarnings = strings.foldLeft(0)((acc, x) => acc + x.split(" ").size)

    assertEquals(expected, sumEarnings)
  }

  @Test
  def whenCallingOwnHigherOrderFunction_thenProperFunctionIsReturned() = {
    def mathOperation(name: String): (Int, Int) => Int = (x: Int, y: Int) => {
      name match {
        case "addition" => x + y
        case "multiplication" => x * y
        case "division" => x/y
        case "subtraction" => x - y
      }
    }

    def add: (Int, Int) => Int = mathOperation("addition")
    def mul: (Int, Int) => Int = mathOperation("multiplication")
    def div: (Int, Int) => Int = mathOperation("division")
    def sub: (Int, Int) => Int = mathOperation("subtraction")

    assertEquals(15, add(10, 5))
    assertEquals(50, mul(10, 5))
    assertEquals(2, div(10, 5))
    assertEquals(5, sub(10, 5))
  }
}