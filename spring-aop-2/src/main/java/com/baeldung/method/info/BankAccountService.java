package com.baeldung.method.info;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.springframework.stereotype.Component;

@Component
public class BankAccountService {

    private final UniformRandomProvider rng = RandomSource.XO_RO_SHI_RO_128_PP.create();

    @AccountOperation(operation = "deposit")
    public void deposit(Account account, Double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    @AccountOperation(operation = "withdraw")
    public void withdraw(Account account, Double amount) throws WithdrawLimitException {

        if (amount > 500.0) {
            throw new WithdrawLimitException("Withdraw limit exceeded.");
        }

        account.setBalance(account.getBalance() - amount);

    }

    public double getBalance() {
        return rng.nextDouble();
    }

}
