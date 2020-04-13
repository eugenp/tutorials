package com.baeldung.dddhexagonalapp.cli.adaptor;

import com.baeldung.dddhexagonalapp.repository.adaptor.CardHolderRepositoryImpl;
import com.baeldung.dddhexagonalapp.coreapp.repository.CardHolderRepository;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderService;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderServiceImpl;
import com.google.inject.AbstractModule;

/**
 * Guice module for binding production dependencies
 */
public class CmdLineModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CardHolderRepository.class).to(CardHolderRepositoryImpl.class);
        bind(CardHolderService.class).to(CardHolderServiceImpl.class);
        bind(CmdLineInterface.class).to(CmdLineInterfaceImpl.class);
    }
}
