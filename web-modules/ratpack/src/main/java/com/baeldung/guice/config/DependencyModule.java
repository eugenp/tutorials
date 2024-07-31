package com.baeldung.guice.config;

import com.baeldung.guice.service.DataPumpService;
import com.baeldung.guice.service.impl.DataPumpServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DependencyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataPumpService.class).to(DataPumpServiceImpl.class)
		  .in(Scopes.SINGLETON);
	}

}
