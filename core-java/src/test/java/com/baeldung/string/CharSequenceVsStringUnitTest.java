package com.baeldung.string;

import org.junit.Test;

public class CharSequenceVsStringUnitTest {

  @Test
  public void differentStringInstantiationsTest() {
    CharSequence firstString = "bealdung";
    String secondString = "baeldung";

    assert firstString != secondString;
  }

  @Test
  public void compareTwoCharSequenceTest() {
    CharSequence charSequence1 = "baeldung_1";
    CharSequence charSequence2 = "baeldung_2";

    assert charSequence1.toString().compareTo(charSequence2.toString()) > 0;
  }
}
