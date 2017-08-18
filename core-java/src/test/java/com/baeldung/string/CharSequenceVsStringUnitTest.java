package com.baeldung.string;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CharSequenceVsStringUnitTest {

  @Test
  public void differentStringInstantiationsTest() {
    CharSequence firstString = "bealdung";
    String secondString = "baeldung";

    assertNotEquals(firstString, secondString);
  }

  @Test
  public void compareTwoCharSequenceTest() {
    CharSequence charSequence1 = "baeldung_1";
    CharSequence charSequence2 = "baeldung_2";

     assertTrue(charSequence1.toString().compareTo(charSequence2.toString()) > 0);
  }
}
