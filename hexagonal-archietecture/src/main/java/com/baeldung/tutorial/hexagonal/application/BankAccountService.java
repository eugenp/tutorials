package com.baeldung.tutorial.hexagonal.application;

import java.math.BigDecimal;

public interface BankAccountService {

        void withdraw(String bankAccountId, BigDecimal withdrawalAmount);
}
