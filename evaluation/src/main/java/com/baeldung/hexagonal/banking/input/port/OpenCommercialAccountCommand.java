package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.baeldung.hexagonal.banking.domain.Company;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class OpenCommercialAccountCommand extends SelfValidating<OpenCommercialAccountCommand> {

    @NotNull
    private final Company accountHolder;

    @NotNull
    private final int pin;

    @NotNull
    private final BigDecimal startingDeposit;

    public OpenCommercialAccountCommand(Company accountHolder, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.startingDeposit = startingDeposit;
        this.validateSelf();
    }

    public Company getAccountHolder() {
        return accountHolder;
    }

    public int getPin() {
        return pin;
    }

    public BigDecimal getStartingDeposit() {
        return startingDeposit;
    }

}
