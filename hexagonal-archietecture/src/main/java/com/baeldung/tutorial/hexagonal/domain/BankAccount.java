package com.baeldung.tutorial.hexagonal.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccount {
        private String accountId;
        private BigDecimal accountBalance;
}
