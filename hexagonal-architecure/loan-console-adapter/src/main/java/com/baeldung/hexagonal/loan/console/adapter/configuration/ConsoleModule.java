package com.baeldung.hexagonal.loan.console.adapter.configuration;

import com.baeldung.hexagonal.loan.console.adapter.services.LoanConsoleService;
import com.baeldung.hexagonal.loan.console.adapter.services.impl.LoanConsoleServiceImpl;
import com.baeldung.hexagonal.loan.core.repository.LoanRepository;
import com.baeldung.hexagonal.loan.core.services.LoanCoreService;
import com.baeldung.hexagonal.loan.core.services.impl.LoanCoreServiceImpl;
import com.baeldung.loan.inmemory.adapter.services.impl.LoanRepositoryImpl;
import com.google.inject.AbstractModule;

public class ConsoleModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(LoanRepository.class).to(LoanRepositoryImpl.class);
        bind(LoanCoreService.class).to(LoanCoreServiceImpl.class);
        bind(LoanConsoleService.class).to(LoanConsoleServiceImpl.class);
	}

}
