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
        Account depositorAccount = accountRepository.findById(depositorId)
                .orElseThrow(IllegalArgumentException::new);
        Account favoredAccount = accountRepository.findById(favoredId)
                .orElseThrow(IllegalArgumentException::new);

        depositorAccount.setBalance(depositorAccount.getBalance().subtract(amount));
        favoredAccount.setBalance(favoredAccount.getBalance().add(amount));

        accountRepository.save(depositorAccount);
        accountRepository.save(favoredAccount);
    }

    public void printReceipt() {
        // logic to print the receipt
    }
}
