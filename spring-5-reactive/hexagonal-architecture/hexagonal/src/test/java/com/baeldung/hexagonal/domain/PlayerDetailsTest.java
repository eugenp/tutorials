package com.baeldung.hexagonal.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * 
 * Unit tests for {@link PlayerDetails}
 *
 */
class PlayerDetailsTest {

  @Test
  void testEquals() {
    PlayerDetails details1 = new PlayerDetails("tom@foo.bar", "11212-123434", "+12323425");
    PlayerDetails details2 = new PlayerDetails("tom@foo.bar", "11212-123434", "+12323425");
    assertEquals(details1, details2);
    PlayerDetails details3 = new PlayerDetails("john@foo.bar", "16412-123439", "+34323432");
    assertNotEquals(details1, details3);
  }  
}
