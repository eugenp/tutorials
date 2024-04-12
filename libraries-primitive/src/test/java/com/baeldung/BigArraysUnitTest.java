package com.baeldung;

import it.unimi.dsi.fastutil.ints.IntBigArrays;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BigArraysUnitTest {

  @Test
  public void givenValidAray_whenWrapped_checkAccessFromIntBigArraysMethodsCorrect() {
    int[] oneDArray = new int[] { 2, 1, 5, 2, 1, 7 };
    int[][] twoDArray = IntBigArrays.wrap(oneDArray.clone());

    int firstIndex = IntBigArrays.get(twoDArray, 0);
    int lastIndex = IntBigArrays.get(twoDArray, IntBigArrays.length(twoDArray)-1);

    assertEquals(2, firstIndex);
    assertEquals(7, lastIndex);

  }

}
