package com.baeldung.hexagonal.exceptions;

public class TimeSheetConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TimeSheetConflictException(String message) {
		super(message);
	}

}
