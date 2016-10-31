package com.baeldung.mdc;

import java.util.Date;

public class TransactionContext {
	
	private String transactionId;
	private String owner;
	private Date createdAt;
	
	public TransactionContext(String transactionId, String owner) {
		this.transactionId = transactionId;
		this.owner = owner;
		this.createdAt = new Date();
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
	
}
