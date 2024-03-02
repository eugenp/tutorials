package com.baeldung.transactionalandasync;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account fromAccount = getAccountInfo(fromId);
        Account toAccount = getAccountInfo(toId);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public Account getAccountInfo(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }
}
