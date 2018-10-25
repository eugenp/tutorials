package com.baeldung.hexagonal.eventlog;

import com.baeldung.hexagonal.domain.PlayerDetails;
/**
 * 
 * Event log for Sweepstake events
 *
 */

public interface SweepstakeEventLog {
	/**
	   * Sweepstake submitted
	   */
	  void ticketSubmitted(PlayerDetails details);

	  /**
	   * error submitting Sweepstake
	   */
	  void ticketSubmitError(PlayerDetails details);

	  /**
	   * Sweepstake did not win
	   */
	  void ticketDidNotWin(PlayerDetails details);

	  /**
	   * Sweepstake won
	   */
	  void ticketWon(PlayerDetails details, int prizeAmount);

	  /**
	   * error paying the prize
	   */
	  void prizeError(PlayerDetails details, int prizeAmount);
}
