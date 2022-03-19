package com.baeldung.hexagonal.architecture.example.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AccountDto {

    private final Long accountNumber;

    private final BigDecimal accountBalance;

    private List<TransactionResponse> transactions;

}