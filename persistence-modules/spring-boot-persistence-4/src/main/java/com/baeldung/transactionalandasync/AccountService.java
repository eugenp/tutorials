package com.baeldung.transactionalandasync;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Async
    public void transferAsync(Long depositorId, Long favoredId, BigDecimal amount) {
        transfer(depositorId, favoredId, amount);

        printReceipt();
    }

    @Transactional
    public void transfer(Long depositorId, Long favoredId, BigDecimal amount) {
        Account depositorAccount = accountRepository.findById(depositorId).get();
        Account favoredAccount = accountRepository.findById(favoredId).get();

        depositorAccount.setBalance(depositorAccount.getBalance().subtract(amount));
        favoredAccount.setBalance(favoredAccount.getBalance().add(amount));

        auditTransferAttempt();

        accountRepository.save(depositorAccount);
        accountRepository.save(favoredAccount);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void auditTransferAttempt() {
        // logic to save the transfer attempt
    }

    public void printReceipt() {
        // logic to print the receipt
    }
}
