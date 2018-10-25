package com.baeldung.hexagonal.eventlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.domain.PlayerDetails;

public class StdOutEventLog implements SweepstakeEventLog{

	private static final Logger LOGGER = LoggerFactory.getLogger(StdOutEventLog.class);
	
	
	@Override
	public void ticketSubmitted(PlayerDetails details) {
		LOGGER.info("Sweepstake entry for {} was submitted",
		        details.getEmail());
		
	}

	@Override
	public void ticketSubmitError(PlayerDetails details) {
		
		 LOGGER.info("Sweepstake entry for {} could not be submitted because of technical error", details.getEmail());
		
	}

	@Override
	public void ticketDidNotWin(PlayerDetails details) {
		LOGGER.info("Sweepstake entry for {} was checked and unfortunately did not win this time.", details.getEmail());
		
	}

	@Override
	public void ticketWon(PlayerDetails details, int prizeAmount) {
		LOGGER.info("Sweepstake entry for {} has won! The bank account {} was deposited with {} credits.",
	            details.getEmail(), details.getBankAccount(), prizeAmount);
		
	}

	@Override
	public void prizeError(PlayerDetails details, int prizeAmount) {
		 LOGGER.error("Sweepstake entry for {} has won! Unfortunately the bank credit transfer of {} failed.",
		            details.getEmail(), prizeAmount);
		
	}

}
