package com.baeldung.hexagonal.architecture.example.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionResponse {

	private BigDecimal amount;

	private String details;

	private Long referenceNo;

	private Long sourceAccountNo;

	private Long targetAccountNo;

}