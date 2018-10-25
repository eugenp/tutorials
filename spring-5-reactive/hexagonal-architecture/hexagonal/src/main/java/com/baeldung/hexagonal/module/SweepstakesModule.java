package com.baeldung.hexagonal.module;

import com.baeldung.hexagonal.banking.MongoBank;
import com.baeldung.hexagonal.banking.WireTransfers;
import com.baeldung.hexagonal.database.MongoTicketRepository;
import com.baeldung.hexagonal.database.SweepstakesRepository;
import com.baeldung.hexagonal.eventlog.MongoEventLog;
import com.baeldung.hexagonal.eventlog.SweepstakeEventLog;
import com.google.inject.AbstractModule;

public class SweepstakesModule extends AbstractModule {
  
		  @Override
		  protected void configure() {
			  bind(SweepstakesRepository.class).to(MongoTicketRepository.class);
			    bind(SweepstakeEventLog.class).to(MongoEventLog.class);
			    bind(WireTransfers.class).to(MongoBank.class);
		}
}
