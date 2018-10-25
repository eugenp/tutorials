package com.baeldung.hexagonal.administration;

import org.slf4j.Logger;

import com.baeldung.hexagonal.domain.SweepstakeAdministration;
import com.baeldung.hexagonal.domain.SweepstakeNumbers;

public class ConsoleAdministrationSrvImpl implements ConsoleAdministrationSrv{

	private final SweepstakeAdministration administration;
	  private final Logger logger;

	  /**
	   * Constructor
	   */
	  public ConsoleAdministrationSrvImpl(SweepstakeAdministration administration, Logger logger) {
	    this.administration = administration;
	    this.logger = logger;
	  }

	  @Override
	  public void getAllSubmittedTickets() {
	    administration.getAllSubmittedTickets().forEach((k, v) -> logger.info("Key: {}, Value: {}", k, v));
	  }

	  @Override
	  public void performSweepstake() {
	    SweepstakeNumbers numbers = administration.performSweepstake();
	    logger.info("The winning numbers: {}", numbers.getNumbersAsString());
	    logger.info("Time to reset the database for next round, eh?");
	  }

	  @Override
	  public void resetSweepstake() {
	    administration.resetSweepstake();
	    logger.info("The sweepstake ticket database was cleared.");
	  }

}
