package com.baeldung.hexagonal.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * Unit tests for {@link Sweepstake}
 *
 */
class SweepstakeNumbersTest {
  
  @Test
  void testGivenNumbers() {
    SweepstakeNumbers numbers = SweepstakeNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    assertEquals(numbers.getNumbers().size(), 4);
    assertTrue(numbers.getNumbers().contains(1));
    assertTrue(numbers.getNumbers().contains(2));
    assertTrue(numbers.getNumbers().contains(3));
    assertTrue(numbers.getNumbers().contains(4));
  }
  
  @Test
  void testNumbersCantBeModified() {
    SweepstakeNumbers numbers = SweepstakeNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    assertThrows(UnsupportedOperationException.class, () -> {
      numbers.getNumbers().add(5);
    });
  }
  
  @Test
  void testRandomNumbers() {
    SweepstakeNumbers numbers = SweepstakeNumbers.createRandom();
    assertEquals(numbers.getNumbers().size(), SweepstakeNumbers.NUM_NUMBERS);
  }
  
  @Test
  void testEquals() {
    SweepstakeNumbers numbers1 = SweepstakeNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    SweepstakeNumbers numbers2 = SweepstakeNumbers.create(
            new HashSet<>(Arrays.asList(1, 2, 3, 4)));
    assertEquals(numbers1, numbers2);
    SweepstakeNumbers numbers3 = SweepstakeNumbers.create(
            new HashSet<>(Arrays.asList(11, 12, 13, 14)));
    assertNotEquals(numbers1, numbers3);
  }
}
