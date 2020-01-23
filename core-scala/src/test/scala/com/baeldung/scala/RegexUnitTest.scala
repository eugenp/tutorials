package com.baeldung.scala

import org.junit.Test
import org.junit.Assert.assertEquals

class RegexUnitTest {
  private val polishPostalCode = "([0-9]{2})\\-([0-9]{3})".r
  private val timestamp = "([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{3})".r
  private val timestampUnanchored = timestamp.unanchored

  @Test
  def givenRegularExpression_whenCallingFindFirstIn_thenShouldFindCorrectMatches(): Unit = {
    val postCode = polishPostalCode.findFirstIn("Warsaw 01-011, Jerusalem Avenue")
    assertEquals(Some("01-011"), postCode)
  }

  @Test
  def givenRegularExpression_whenCallingFindFirstMatchIn_thenShouldFindCorrectMatches(): Unit = {
    val postCodes = polishPostalCode.findFirstMatchIn("Warsaw 01-011, Jerusalem Avenue")
    assertEquals(Some("011"), for (m <- postCodes) yield m.group(2))
  }

  @Test
  def givenRegularExpression_whenCallingFindAllIn_thenShouldFindCorrectMatches(): Unit = {
    val postCodes = polishPostalCode.findAllIn("Warsaw 01-011, Jerusalem Avenue, Cracow 30-059, Mickiewicza Avenue")
      .toList
    assertEquals(List("01-011", "30-059"), postCodes)

    polishPostalCode.findAllIn("Warsaw 01-011, Jerusalem Avenue, Cracow 30-059, Mickiewicza Avenue")
  }

  @Test
  def givenRegularExpression_whenCallingFindAlMatchlIn_thenShouldFindCorrectMatches(): Unit = {
    val postCodes = polishPostalCode.findAllMatchIn("Warsaw 01-011, Jerusalem Avenue, Cracow 30-059, Mickiewicza Avenue")
      .toList
    val postalDistricts = for (m <- postCodes) yield m.group(1)
    assertEquals(List("01", "30"), postalDistricts)
  }

  @Test
  def givenRegularExpression_whenExtractingValues_thenShouldExtractCorrectValues(): Unit = {
    val description = "11:34:01.411" match {
      case timestamp(hour, minutes, _, _) => s"It's $minutes minutes after $hour"
    }

    assertEquals("It's 34 minutes after 11", description)
  }

  @Test
  def givenUnanchoredRegularExpression_whenExtractingValues_thenShouldExtractCorrectValues(): Unit = {
    val description = "Timestamp: 11:34:01.411 error appeared" match {
      case timestampUnanchored(hour, minutes, _, _) => s"It's $minutes minutes after $hour"
    }

    assertEquals("It's 34 minutes after 11", description)
  }

  @Test
  def givenRegularExpression_whenCallingReplaceAllIn_thenShouldReplaceText(): Unit = {
    val minutes = timestamp.replaceAllIn("11:34:01.311", m => m.group(2))

    assertEquals("34", minutes)
  }

  @Test
  def givenRegularExpression_whenCallingReplaceAllInWithMatcher_thenShouldReplaceText(): Unit = {
    val secondsThatDayInTotal = timestamp.replaceAllIn("11:34:01.311", _ match {
      case timestamp(hours, minutes, seconds, _) => s"$hours-$minutes"
    })

    assertEquals("11-34", secondsThatDayInTotal)
  }
}
