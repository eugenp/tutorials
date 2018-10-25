package com.baeldung.hexagonal.banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * Tests for banking
 *
 */
class InMemoryBankTest {

  private final WireTransfers bank = new InMemoryBank();
  
  @Test
  void testInit() {
    assertEquals(0, bank.getFunds("foo"));
    bank.setFunds("foo", 100);
    assertEquals(100, bank.getFunds("foo"));
    bank.setFunds("bar", 150);
    assertEquals(150, bank.getFunds("bar"));
    assertTrue(bank.transferFunds(50, "bar", "foo"));
    assertEquals(150, bank.getFunds("foo"));
    assertEquals(100, bank.getFunds("bar"));
  }
}
