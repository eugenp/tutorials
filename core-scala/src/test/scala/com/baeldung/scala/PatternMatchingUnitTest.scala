package com.baeldung.scala

import org.junit.Assert.assertEquals
import org.junit.Test

class PatternMatchingUnitTest {
  @Test
  def whenAMammalIsGivenToTheMatchExpression_ThenItsRecognizedAsMammal(): Unit = {
    val result = new PatternMatching().caseClassesPatternMatching(Mammal("Lion", fromSea = false))
    assertEquals("I'm a Lion, a kind of mammal. Am I from the sea? false", result)
  }

  @Test
  def whenABirdIsGivenToTheMatchExpression_ThenItsRecognizedAsBird(): Unit = {
    val result = new PatternMatching().caseClassesPatternMatching(Bird("Pigeon"))
    assertEquals("I'm a Pigeon, a kind of bird", result)
  }

  @Test
  def whenAnUnkownAnimalIsGivenToTheMatchExpression_TheDefaultClauseIsUsed(): Unit = {
    val result = new PatternMatching().caseClassesPatternMatching(Fish("Tuna"))
    assertEquals("I'm an unknown animal", result)
  }

  @Test
  def zeroConstant(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(0)
    assertEquals("I'm equal to zero", result)
  }

  @Test
  def doubleConstant(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(4.5d)
    assertEquals("I'm a double", result)
  }

  @Test
  def booleanConstant(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(false)
    assertEquals("I'm the contrary of true", result)
  }

  @Test
  def unknownConstant(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(true)
    assertEquals("I'm unknown and equal to true", result)
  }
}
