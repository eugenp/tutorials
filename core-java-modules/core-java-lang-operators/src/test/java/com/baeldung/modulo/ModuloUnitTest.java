package com.baeldung.modulo;

import org.junit.Test;
import static org.assertj.core.api.Java6Assertions.*;

public class ModuloUnitTest {

  @Test
  public void whenIntegerDivision_thenLosesRemainder(){
    assertThat(11 / 4).isEqualTo(2);
  }

  @Test
  public void whenDoubleDivision_thenKeepsRemainder(){
    assertThat(11 / 4.0).isEqualTo(2.75);
  }

  @Test
  public void whenModulo_thenReturnsRemainder(){
    assertThat(11 % 4).isEqualTo(3);
  }

  @Test(expected = ArithmeticException.class)
  public void whenDivisionByZero_thenArithmeticException(){
    double result = 1 / 0;
  }

  @Test(expected = ArithmeticException.class)
  public void whenModuloByZero_thenArithmeticException(){
    double result = 1 % 0;
  }

  @Test
  public void whenDivisorIsOddAndModulusIs2_thenResultIs1(){
    assertThat(3 % 2).isEqualTo(1);
  }

  @Test
  public void whenDivisorIsEvenAndModulusIs2_thenResultIs0(){
    assertThat(4 % 2).isEqualTo(0);
  }

  @Test
  public void whenItemsIsAddedToCircularQueue_thenNoArrayIndexOutOfBounds(){
    int QUEUE_CAPACITY= 10;
    int[] circularQueue = new int[QUEUE_CAPACITY];
    int itemsInserted = 0;
    for (int value = 0; value < 1000; value++) {
      int writeIndex = ++itemsInserted % QUEUE_CAPACITY;
      circularQueue[writeIndex] = value;
    }
  }

}
