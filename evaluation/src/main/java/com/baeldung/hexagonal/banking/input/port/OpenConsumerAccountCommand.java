package com.baeldung.hexagonal.banking.input.port;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.baeldung.hexagonal.banking.domain.Person;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class OpenConsumerAccountCommand extends SelfValidating<OpenConsumerAccountCommand> {

    @NotNull
    private final Person accountHolder;

    @NotNull
    private final int pin;

    @NotNull
    private final BigDecimal startingDeposit;

    public OpenConsumerAccountCommand(Person accountHolder, int pin, BigDecimal startingDeposit) {
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.startingDeposit = startingDeposit;
        this.validateSelf();
    }

    public Person getAccountHolder() {
        return accountHolder;
    }

    public int getPin() {
        return pin;
    }

    public BigDecimal getStartingDeposit() {
        return startingDeposit;
    }

}
