package com.baeldung.mdc;

import java.util.Date;

public class Transaction {
	
	private String transactionId;
	private String owner;
	private Date createdAt;
	private Long amount;
	
	public Transaction(String transactionId, String owner, long amount) {
		this.transactionId = transactionId;
		this.owner = owner;
		this.createdAt = new Date();
		this.amount = amount;
	}

	public String getOwner() {
		return owner;
	}

	public String getTransactionId() {
		return transactionId;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public Long getAmount() {
		return amount;
	}
	
}
