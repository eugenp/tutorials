package com.baeldung.hexagonal.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test Sweepstake for equality
 */
class SweepstakeTest {

  @Test
  void testEquals() {
    PlayerDetails details1 = new PlayerDetails("alag@foo.com", "1212-121212", "+34332322");
    SweepstakeNumbers numbers1 = SweepstakeNumbers.create(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)));
    Sweepstake ticket1 = new Sweepstake(new SweepstakeId(), details1, numbers1);
    PlayerDetails details2 = new PlayerDetails("alag@foo.com", "1212-121212", "+34332322");
    SweepstakeNumbers numbers2 = SweepstakeNumbers.create(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)));
    Sweepstake ticket2 = new Sweepstake(new SweepstakeId(), details2, numbers2);
    assertEquals(ticket1, ticket2);
    PlayerDetails details3 = new PlayerDetails("visa@foo.bar", "1223-121212", "+49332322");
    SweepstakeNumbers numbers3 = SweepstakeNumbers.create(new HashSet<Integer>(Arrays.asList(1, 2, 3, 8)));
    Sweepstake ticket3 = new Sweepstake(new SweepstakeId(), details3, numbers3);
    assertNotEquals(ticket1, ticket3);
  }
}
