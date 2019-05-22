package com.baeldung.arch.hexagonal.core.exception;

public class DuplicateUserIdException extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicateUserIdException() {
		super("Duplicate user, try updating instead!");
	}
}