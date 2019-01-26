package com.baeldung.tutorial.hexagonal.message;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalRequestMessage {

    private String bankAccountId;
    private BigDecimal amount;
}
