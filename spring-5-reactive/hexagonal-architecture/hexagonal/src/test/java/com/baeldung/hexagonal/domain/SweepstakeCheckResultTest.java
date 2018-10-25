package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.domain.SweepstakeCheckResult.CheckResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * 
 * Unit tests for {@link SweepstakeCheckResult}
 *
 */
class SweepstakeCheckResultTest {

  @Test
  void testEquals() {
    SweepstakeCheckResult result1 = new SweepstakeCheckResult(CheckResult.NO_PRIZE);
    SweepstakeCheckResult result2 = new SweepstakeCheckResult(CheckResult.NO_PRIZE);
    assertEquals(result1, result2);
    SweepstakeCheckResult result3 = new SweepstakeCheckResult(CheckResult.WIN_PRIZE, 300000);
    assertNotEquals(result1, result3);
  } 
}
