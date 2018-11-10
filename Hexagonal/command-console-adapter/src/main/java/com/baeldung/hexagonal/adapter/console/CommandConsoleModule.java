
package com.baeldung.hexagonal.adapter.console;

import com.baeldung.hexagonal.adapter.repository.InMemoryCusotmerRepositoryImpl;
import com.baeldung.hexagonal.repository.CustomerRepository;
import com.baeldung.hexagonal.service.CustomerService;
import com.baeldung.hexagonal.service.CustomerServiceImpl;
import com.google.inject.AbstractModule;

/**
 * Guice module for binding production dependencies
 */
public class CommandConsoleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CustomerRepository.class).to(InMemoryCusotmerRepositoryImpl.class);
        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(CommandInterface.class).to(CommandInterfaceImpl.class);
    }
}
