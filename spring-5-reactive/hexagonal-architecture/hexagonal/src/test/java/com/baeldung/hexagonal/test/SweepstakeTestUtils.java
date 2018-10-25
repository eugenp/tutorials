package com.baeldung.hexagonal.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.baeldung.hexagonal.domain.SweepstakeNumbers;
import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.domain.PlayerDetails;

/**
 * 
 * Utilities for Sweepstake tests
 *
 */
public class SweepstakeTestUtils {

  /**
   * @return Sweepstake ticket
   */
  public static Sweepstake createSweepstake() {
    return createSweepstake("alag@bar.com", "12231-213132", "+99324554", new HashSet<>(Arrays.asList(1, 2, 3, 4)));
  }
  
  /**
   * @return Sweepstake
   */
  public static Sweepstake createSweepstake(String email, String account, String phone,
      Set<Integer> givenNumbers) {
    PlayerDetails details = new PlayerDetails(email, account, phone);
    SweepstakeNumbers numbers = SweepstakeNumbers.create(givenNumbers);
    return new Sweepstake(new SweepstakeId(), details, numbers);
  }
}
