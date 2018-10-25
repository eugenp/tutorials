package com.baeldung.hexagonal.module;

import com.baeldung.hexagonal.banking.InMemoryBank;
import com.baeldung.hexagonal.banking.WireTransfers;
import com.baeldung.hexagonal.database.InMemoryTicketRepository;
import com.baeldung.hexagonal.database.SweepstakesRepository;
import com.baeldung.hexagonal.eventlog.StdOutEventLog;
import com.baeldung.hexagonal.eventlog.SweepstakeEventLog;
import com.google.inject.AbstractModule;
/**
 * Guice module for testing dependencies
 */
public class SweepstakeTestingModule extends AbstractModule {
	  @Override
	  protected void configure() {
	    bind(SweepstakesRepository.class).to(InMemoryTicketRepository.class);
	    bind(SweepstakeEventLog.class).to(StdOutEventLog.class);
	    bind(WireTransfers.class).to(InMemoryBank.class);
	  }
	}
