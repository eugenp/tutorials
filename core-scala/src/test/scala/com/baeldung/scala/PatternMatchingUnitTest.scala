package com.baeldung.scala

import java.io.FileNotFoundException

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
  def whenTheConstantZeroIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(0)
    assertEquals("I'm equal to zero", result)
  }

  @Test
  def whenFourAndAHalfIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(4.5d)
    assertEquals("I'm a double", result)
  }

  @Test
  def whenTheBooleanFalseIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(false)
    assertEquals("I'm the contrary of true", result)
  }

  @Test
  def whenAnUnkownConstantIsPassed_ThenTheDefaultPatternIsUsed(): Unit = {
    val result = new PatternMatching().constantsPatternMatching(true)
    assertEquals("I'm unknown and equal to true", result)
  }

  @Test
  def whenASingleElementListIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List("String"))
    assertEquals("I'm a list with one element: String", result)
  }

  @Test
  def whenAMultipleElementsListIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List("Multiple", "Elements"))
    assertEquals("I'm a list with one or multiple elements: List(Multiple, Elements)", result)
  }

  @Test
  def whenAVectorBeginningWithOneAndTwoIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(Vector(1, 2, 3))
    assertEquals("I'm a vector: Vector(1, 2, 3)", result)
  }

  @Test
  def whenANotMatchingVectorIsPassed_ThenItShouldntMatchAndEnterTheDefaultClause(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(Vector(2, 1))
    assertEquals("I'm an unrecognized sequence. My value: Vector(2, 1)", result)
  }

  @Test
  def whenAnEmptyListIsPassed_ThenItShouldntMatchAndEnterTheDefaultClause(): Unit = {
    val result = new PatternMatching().sequencesPatternMatching(List())
    assertEquals("I'm an unrecognized sequence. My value: List()", result)
  }

  @Test
  def whenATwoElementsTupleIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First", "Second"))
    assertEquals("I'm a tuple with two elements: First & Second", result)
  }

  @Test
  def whenAThreeElementsTupleIsPassed_ThenItMatchesTheCorrespondingPattern(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First", "Second", "Third"))
    assertEquals("I'm a tuple with three elements: First & Second & Third", result)
  }

  @Test
  def whenAnoterKindOfTupleIsPassed_ThenItShouldntMatchAndReturnTheDefaultPattern(): Unit = {
    val result = new PatternMatching().tuplesPatternMatching(("First"))
    assertEquals("Unrecognized pattern. My value: First", result)
  }

  @Test
  def whenAStringConsistingOfNumericsOnlyIsPassed_ThenItShouldMatchTheNumericRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("123")
    assertEquals("I'm a numeric with value 123", result)
  }

  @Test
  def whenAStringConsistignOfAlphabeticsOnlyIsPassed_ThenItShouldMatchTheAlphabeticRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("abc")
    assertEquals("I'm an alphabetic with value abc", result)
  }

  @Test
  def whenAStringConsistignOfAlphanumericsOnlyIsPassed_ThenItShouldMatchTheAlphanumericRegex(): Unit = {
    val result = new PatternMatching().regexPatterns("abc123")
    assertEquals("I'm an alphanumeric with value abc123", result)
  }

  @Test
  def whenAnotherTypeOfStringIsPassed_ThenItShouldntMatchAndReturnTheDefaultPattern(): Unit = {
    val result = new PatternMatching().regexPatterns("abc_123")
    assertEquals("I contain other characters than alphanumerics. My value abc_123", result)
  }

  @Test
  def whenAFilledOptionIsPassed_ThenItShouldMatchTheSomeClause(): Unit = {
    val result = new PatternMatching().optionsPatternMatching(Option.apply("something"))
    assertEquals("I'm not an empty option. Value something", result)
  }

  @Test
  def whenAnEmptyOptionIsPassed_ThenItShouldMatchTheNoneClause(): Unit = {
    val result = new PatternMatching().optionsPatternMatching(Option.empty)
    assertEquals("I'm an empty option", result)
  }

  @Test
  def whenAListWithAcceptedSizeIsPassed_ThenThePositiveMessageIsSent(): Unit = {
    val result = new PatternMatching().patternGuards(List(1, 2), 3)
    assertEquals("List is of acceptable size", result)
  }

  @Test
  def whenAListWithAnUnacceptedSizeIsPassed_ThenTheNegativeMessageIsSent(): Unit = {
    val result = new PatternMatching().patternGuards(List(1, 2, 3, 4), 3)
    assertEquals("List has not an acceptable size", result)
  }

  @Test
  def whenAStringWithAcceptedSizeIsPassed_ThenThePositiveMessageIsSent(): Unit = {
    val result = new PatternMatching().patternGuards("OK", 3)
    assertEquals("String is of acceptable size", result)
  }

  @Test
  def whenAStringWithAnUnacceptedSizeIsPassed_ThenTheNegativeMessageIsSent(): Unit = {
    val result = new PatternMatching().patternGuards("Not OK", 3)
    assertEquals("String has not an acceptable size", result)
  }

  @Test
  def whenAnObjectWhichIsNotAListOrAStringIsPassed_thenTheDefaultClauseIsUsed(): Unit = {
    val result = new PatternMatching().patternGuards(1, 1)
    assertEquals("Input is neither a List or a String", result)
  }

  @Test
  def whenACardSuitIsPassed_ThenTheCorrespondingMatchCaseClauseIsUsed(): Unit = {
    assertEquals("Card is spike", new PatternMatching().sealedClass(Spike()))
    assertEquals("Card is club", new PatternMatching().sealedClass(Club()))
    assertEquals("Card is heart", new PatternMatching().sealedClass(Heart()))
    assertEquals("Card is diamond", new PatternMatching().sealedClass(Diamond()))
  }

  @Test
  def whenAnObjectWithExtractorIsPassed_ThenTheExtractedValueIsUsedInTheCaseClause(): Unit = {
    val person = Person("John Smith")
    val result = new PatternMatching().extractors(person)
    assertEquals("My initials are J. S.", result)
  }

  @Test
  def whenAnObjectWithExtractorIsPassed_AndTheValueIsEmpty_ThenTheDefaultCaseClauseIsUsed(): Unit = {
    val person = Person("")
    val result = new PatternMatching().extractors(person)
    assertEquals("Could not extract initials", result)
  }

  @Test
  def whenAListOfRandomElementsIsPassed_ThenOnlyTheIntegersBelowTenAreReturned(): Unit = {
    val input = List(1, 2, "5", 11, true)
    val result = new PatternMatching().closuresPatternMatching(input)
    assertEquals(List(1, 2), result)
  }

  @Test
  def whenAnExceptionIsPassed_ThenTheCorrespondingMessageIsReturned(): Unit = {
    val pm = new PatternMatching()

    val iae = new IllegalArgumentException()
    val re = new RuntimeException()
    val fnfe = new FileNotFoundException()

    assertEquals("It's an IllegalArgumentException", pm.catchBlocksPatternMatching(iae))
    assertEquals("It's a RuntimeException", pm.catchBlocksPatternMatching(re))
    assertEquals("It's an unknown kind of exception", pm.catchBlocksPatternMatching(fnfe))
  }
}


