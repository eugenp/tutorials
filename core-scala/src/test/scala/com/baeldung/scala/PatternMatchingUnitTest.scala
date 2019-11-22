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

  @Test
  def singleElementList(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List("String"))
    assertEquals("I'm a list with one element", result)
  }

  @Test
  def singleOrMoreElementsList(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List("Multiple", "Elements"))
    assertEquals("I'm a list with one or multiple elements", result)
  }

  @Test
  def vectorWithOneAndTwo(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(Vector(1, 2, 3))
    assertEquals("I'm a vector. My two first elements are '1' & '2'", result)
  }

  @Test
  def otherSequences(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(Vector(2, 1))
    assertEquals("I'm an unrecognized sequence. My value: Vector(2, 1)", result)
  }

  @Test
  def emptyList(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List())
    assertEquals("I'm an unrecognized sequence. My value: List()", result)
  }

  @Test
  def tupleWithTwoElements(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First", "Second"))
    assertEquals("I'm a tuple with two elements: First & Second", result)
  }

  @Test
  def threeElementsTuple(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First", "Second", "Third"))
    assertEquals("I'm a tuple with three elements: First & Second & Third", result)
  }

  @Test
  def unrecognizedTuple(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First"))
    assertEquals("Unrecognized pattern. My value: First", result)
  }

  @Test
  def numericRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("123")
    assertEquals("I'm a numeric with value 123", result)
  }

  @Test
  def alphabeticRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("abc")
    assertEquals("I'm an alphabetic with value abc", result)
  }

  @Test
  def alphaNumericRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("abc123")
    assertEquals("I'm an alphanumeric with value abc123", result)
  }

  @Test
  def otherRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("abc_123")
    assertEquals("I contain other characters than alphanumerics. My value abc_123", result)
  }

  @Test
  def some(): Unit = {
    val result = new PatternMatching().optionsPatternMatching(Option.apply("something"))
    assertEquals("I'm not an empty option. Value something", result)
  }

  @Test
  def none(): Unit = {
    val result = new PatternMatching().optionsPatternMatching(Option.empty)
    assertEquals("I'm an empty option", result)
  }

  @Test
  def default(): Unit = {
    val result = new PatternMatching().defaultPatternMatching("Five")
    assertEquals(-1, result)
  }

  @Test
  def listAccSize(): Unit = {
    val result = new PatternMatching().patternGuards(List(1, 2), 3)
    assertEquals("List is of acceptable size", result)
  }

  @Test
  def listUnaccSize(): Unit = {
    val result = new PatternMatching().patternGuards(List(1, 2, 3, 4), 3)
    assertEquals("List has not an acceptable size", result)
  }

  @Test
  def stringAccSize(): Unit = {
    val result = new PatternMatching().patternGuards("OK", 3)
    assertEquals("String is of acceptable size", result)
  }

  @Test
  def stringUnaccSize(): Unit = {
    val result = new PatternMatching().patternGuards("Not OK", 3)
    assertEquals("String has not an acceptable size", result)
  }

  @Test
  def patternGuardWithUnknownType(): Unit = {
    val result = new PatternMatching().patternGuards(1, 1)
    assertEquals("Input is neither a List or a String", result)
  }

  @Test
  def cardSuit(): Unit = {
    assertEquals("Card is spike", new PatternMatching().sealedClass(Spike()))
    assertEquals("Card is club", new PatternMatching().sealedClass(Club()))
    assertEquals("Card is heart", new PatternMatching().sealedClass(Heart()))
    assertEquals("Card is diamond", new PatternMatching().sealedClass(Diamond()))
  }

  @Test
  def extractor(): Unit = {
    val person = Person("John Smith")
    val result = new PatternMatching().extractors(person)
    assertEquals("My initials are J. S.", result)
  }

  @Test
  def extractorEmpty(): Unit = {
    val person = Person("")
    val result = new PatternMatching().extractors(person)
    assertEquals("Could not extract initials", result)
  }
}


