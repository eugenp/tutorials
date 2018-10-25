package com.baeldung.hexagonal.domain;

import java.util.Optional;

import com.baeldung.hexagonal.database.SweepstakesRepository;

public class SweepstakeUtils {
	private SweepstakeUtils() {
	  }

	  /**
	   * Checks if lottery ticket has won
	   */
	  public static SweepstakeCheckResult checkTicketForPrize(SweepstakesRepository repository, SweepstakeId id,
	                                                      SweepstakeNumbers winningNumbers) {
	    Optional<Sweepstake> optional = repository.findById(id);
	    if (optional.isPresent()) {
	      if (optional.get().getNumbers().equals(winningNumbers)) {
	        return new SweepstakeCheckResult(SweepstakeCheckResult.CheckResult.WIN_PRIZE, 1000);
	      } else {
	        return new SweepstakeCheckResult(SweepstakeCheckResult.CheckResult.NO_PRIZE);
	      }
	    } else {
	      return new SweepstakeCheckResult(SweepstakeCheckResult.CheckResult.TICKET_NOT_SUBMITTED);
	    }
	  }
}
