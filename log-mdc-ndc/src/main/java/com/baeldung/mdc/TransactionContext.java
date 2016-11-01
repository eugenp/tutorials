package com.baeldung.mdc;

import java.util.Date;

public class TransactionContext {
	
	private String transactionId;
	private String owner;
	private Date createdAt;
	private Long amount;
	
	public TransactionContext(String transactionId, String owner) {
		this.transactionId = transactionId;
		this.owner = owner;
		this.createdAt = new Date();
		this.amount = (long) (Math.random()*100);
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
