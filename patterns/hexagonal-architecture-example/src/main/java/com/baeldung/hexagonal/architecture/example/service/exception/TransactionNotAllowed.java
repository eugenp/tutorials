package com.baeldung.hexagonal.architecture.example.service.exception;

public class TransactionNotAllowed extends RuntimeException {

	public TransactionNotAllowed(String message) {
		super(message);
	}
}