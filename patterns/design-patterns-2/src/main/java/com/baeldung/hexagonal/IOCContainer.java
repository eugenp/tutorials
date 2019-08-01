package com.baeldung.hexagonal;

import com.baeldung.hexagonal.central.service.AccountService;
import com.baeldung.hexagonal.central.service.IAccountService;
import com.baeldung.hexagonal.input.ConsoleUI;
import com.baeldung.hexagonal.output.InMemoryAccountRepository;

public class IOCContainer {

    private ConsoleUI consoleAdapter;
    private AccountService accountService;
    private InMemoryAccountRepository inMemoryAccountRepository;

    public <T> T get(Class<T> clazz) {

        if (ConsoleUI.class.isAssignableFrom(clazz)) {
            return (T) getConsoleAdapter();
        }

        if (IAccountService.class.isAssignableFrom(clazz)) {
            return (T) getAccountService();
        }

        if (InMemoryAccountRepository.class.isAssignableFrom(clazz)) {
            return (T) getInMemoryAccountRepository();
        }

        return null;
    }

    private ConsoleUI getConsoleAdapter() {
        if (consoleAdapter == null) {
            consoleAdapter = new ConsoleUI();
            consoleAdapter.setAccountService(getAccountService());
        }
        return consoleAdapter;
    }

    private IAccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService();
            accountService.setAccountRepository(getInMemoryAccountRepository());
        }
        return accountService;
    }

    private InMemoryAccountRepository getInMemoryAccountRepository() {
        if (inMemoryAccountRepository == null) {
            inMemoryAccountRepository = new InMemoryAccountRepository();
        }
        return inMemoryAccountRepository;
    }

}
