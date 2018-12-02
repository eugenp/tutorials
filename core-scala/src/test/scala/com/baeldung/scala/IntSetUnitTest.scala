package com.baeldung.scala

import org.junit.Assert.assertFalse
import org.junit.Test

class IntSetUnitTest {

  @Test
  def givenSetof1To10_whenContains11Called_thenFalse() = {

    // Set up a set containing integers 1 to 10.
    val set1To10 =
      Range(1, 10)
        .foldLeft(new EmptyIntSet() : IntSet) {
          (x, y) => x incl y
        }

    assertFalse(set1To10 contains 11)
  }

}