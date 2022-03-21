package com.baeldung.hexagonal.architecture.example.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

	@NotNull
	@Positive
	private BigDecimal amount;

	private String details;

	private Long referenceNo;

	@NotNull
	private Long sourceAccountNo;

	@NotNull
	private Long targetAccountNo;

}
