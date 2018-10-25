package com.baeldung.hexagonal.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for Sweepstake entry
 */
class SweepstakeIdTest {

  @Test
  void testEquals() {
    SweepstakeId ticketId1 = new SweepstakeId();
    SweepstakeId ticketId2 = new SweepstakeId();
    SweepstakeId ticketId3 = new SweepstakeId();
    assertNotEquals(ticketId1, ticketId2);
    assertNotEquals(ticketId2, ticketId3);
    SweepstakeId ticketId4 = new SweepstakeId(ticketId1.getId());
    assertEquals(ticketId1, ticketId4);
  }
}
