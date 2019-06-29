package com.baeldung.inprogress.hexagonal.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class MemoryReadOnlyAccountRepositoryImpl implements ReadOnlyAccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryReadOnlyAccountRepositoryImpl.class);

    private static List<Account> IN_MEM_ACCOUNTS;

    static {
        IN_MEM_ACCOUNTS = new ArrayList<>();
        IN_MEM_ACCOUNTS.add(new Account("1", "SAMPLE-1"));
        IN_MEM_ACCOUNTS.add(new Account("2", "SAMPLE-2"));
    }

    @Override
    public Optional<Account> getAccountById(String id) {
        logger.info("Getting account via Memory Adapter");
        return IN_MEM_ACCOUNTS.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }
}
