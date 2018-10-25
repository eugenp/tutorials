package com.baeldung.hexagonal.domain;

import java.util.Map;

import com.baeldung.hexagonal.banking.WireTransfers;
import com.baeldung.hexagonal.database.SweepstakesRepository;
import com.baeldung.hexagonal.eventlog.SweepstakeEventLog;
import com.google.inject.Inject;

public class SweepstakeAdministration {
	private final SweepstakesRepository repository;
	  private final SweepstakeEventLog notifications;
	  private final WireTransfers wireTransfers;

	  /**
	   * Constructor
	   */
	  @Inject
	  public SweepstakeAdministration(SweepstakesRepository repository, SweepstakeEventLog notifications,
	                               WireTransfers wireTransfers) {
	    this.repository = repository;
	    this.notifications = notifications;
	    this.wireTransfers = wireTransfers;
	  }

	  /**
	   * Get all the  entries submitted for sweepstake
	   */
	  public Map<SweepstakeId, Sweepstake> getAllSubmittedTickets() {
	    return repository.findAll();
	  }

	  /**
	   * Draw sweepstake numbers
	   */
	  public SweepstakeNumbers performSweepstake() {
		  SweepstakeNumbers numbers = SweepstakeNumbers.createRandom();
	    Map<SweepstakeId, Sweepstake> tickets = getAllSubmittedTickets();
	    for (SweepstakeId id : tickets.keySet()) {
	    	SweepstakeCheckResult result = SweepstakeUtils.checkTicketForPrize(repository, id, numbers);
	      if (result.getResult().equals(SweepstakeCheckResult.CheckResult.WIN_PRIZE)) {
	        boolean transferred = wireTransfers.transferFunds(SweepstakeConstants.PRIZE_AMOUNT,
	        		SweepstakeConstants.SERVICE_BANK_ACCOUNT, tickets.get(id).getPlayerDetails().getBankAccount());
	        if (transferred) {
	          notifications.ticketWon(tickets.get(id).getPlayerDetails(), SweepstakeConstants.PRIZE_AMOUNT);
	        } else {
	          notifications.prizeError(tickets.get(id).getPlayerDetails(), SweepstakeConstants.PRIZE_AMOUNT);
	        }
	      } else if (result.getResult().equals(SweepstakeCheckResult.CheckResult.NO_PRIZE)) {
	        notifications.ticketDidNotWin(tickets.get(id).getPlayerDetails());
	      }
	    }
	    return numbers;
	  }

	  /**
	   * Begin new sweepstake
	   */
	  public void resetSweepstake() {
	    repository.deleteAll();
	  }
}
