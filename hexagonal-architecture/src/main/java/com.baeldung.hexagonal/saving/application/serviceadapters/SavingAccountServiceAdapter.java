package com.baeldung.hexagonal.saving.application.serviceadapters;

import com.baeldung.hexagonal.saving.application.port.in.SavingAccountService;
import com.baeldung.hexagonal.saving.application.port.out.SavingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SavingAccountServiceAdapter implements SavingAccountService {

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    public BigDecimal getBalance(String accountNumber) {
        return savingAccountRepository.getBalance(accountNumber);
    }
}
