package com.baeldung.hexagonal.administration;

public interface ConsoleAdministrationSrv {
	/**
	   * Get all submitted tickets
	   */
	  void getAllSubmittedTickets();

	  /**
	   * Draw Sweepstake entries
	   */
	  void performSweepstake();

	  /**
	   * Begin new Sweepstake round
	   */
	  void resetSweepstake();
}
