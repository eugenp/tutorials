package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.baeldung.hexagonal.banking.domain.AccountHolder;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class OpenAccountCommand {

    @NotNull
    private final AccountHolder accountHolder;

    @NotNull
    private final int pin;

    @NotNull
    private final BigDecimal startingDeposit;

    public OpenAccountCommand(AccountHolder accountHolder, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.startingDeposit = startingDeposit;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public int getPin() {
        return pin;
    }

    public BigDecimal getStartingDeposit() {
        return startingDeposit;
    }

}
