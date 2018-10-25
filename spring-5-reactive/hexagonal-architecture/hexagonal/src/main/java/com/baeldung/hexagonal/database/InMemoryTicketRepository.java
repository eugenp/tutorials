package com.baeldung.hexagonal.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;

/**
 * 
 * Mock Database for sweepstakes
 *
 */
public class InMemoryTicketRepository implements SweepstakesRepository {
	private static Map<SweepstakeId, Sweepstake> tickets = new HashMap<>();

	  @Override
	  public Optional<Sweepstake> findById(SweepstakeId id) {
		  Sweepstake ticket = tickets.get(id);
	    if (ticket == null) {
	      return Optional.empty();
	    } else {
	      return Optional.of(ticket);
	    }
	  }

	  @Override
	  public Optional<SweepstakeId> save(Sweepstake ticket) {
		  SweepstakeId id = new SweepstakeId();
	    tickets.put(id, ticket);
	    return Optional.of(id);
	  }

	  @Override
	  public Map<SweepstakeId, Sweepstake> findAll() {
	    return tickets;
	  }

	  @Override
	  public void deleteAll() {
	    tickets.clear();
	  }

}
