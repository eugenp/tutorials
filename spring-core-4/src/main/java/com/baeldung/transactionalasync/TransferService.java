package com.baeldung.transactionalasync;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class TransferService {
    private AccountRepository accountRepository;

    @Transactional
    public void transfer(String fromAccount, String toAccount, BigDecimal amount) {
        Account source = accountRepository.findById(1L);
        Account destination = accountRepository.findById(2L);

        source.withdraw(amount);
        destination.deposit(amount);

        accountRepository.save(source);
        accountRepository.save(destination);
    }

    @Async
    public void asyncTransfer(String fromAccount, String toAccount, BigDecimal amount) {
        transfer(fromAccount, toAccount, amount);
    }
}
