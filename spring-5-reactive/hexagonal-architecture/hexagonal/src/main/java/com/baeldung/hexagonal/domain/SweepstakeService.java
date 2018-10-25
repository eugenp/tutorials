package com.baeldung.hexagonal.domain;

import java.util.Optional;

import com.baeldung.hexagonal.database.SweepstakesRepository;
import com.baeldung.hexagonal.eventlog.SweepstakeEventLog;
import com.google.inject.Inject;

public class SweepstakeService {
	private final SweepstakesRepository repository;
	  private final SweepstakeEventLog notifications;
	  /**
	   * Constructor
	   */
	  @Inject
	  public SweepstakeService(SweepstakesRepository repository, SweepstakeEventLog notifications) {
	    this.repository = repository;
	    this.notifications = notifications;
	  }

	  /**
	   * Submit entry to participate in the sweepstake
	   */
	  public Optional<SweepstakeId> submitTicket(Sweepstake ticket) {
	  
	    Optional<SweepstakeId> optional = repository.save(ticket);
	    if (optional.isPresent()) {
	      notifications.ticketSubmitted(ticket.getPlayerDetails());
	    }
	    return optional;
	  }

	  /**
	   * Check if entry has won
	   */
	  public SweepstakeCheckResult checkTicketForPrize(SweepstakeId id, SweepstakeNumbers winningNumbers) {
	    return SweepstakeUtils.checkTicketForPrize(repository, id, winningNumbers);
	  }
}
