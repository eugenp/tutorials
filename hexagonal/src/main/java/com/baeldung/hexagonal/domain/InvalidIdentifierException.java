package com.baeldung.hexagonal.domain;

class InvalidIdentifierException extends PatientValidationException {

	private InvalidIdentifierException(String message) {
		super(message);
	}

	static InvalidIdentifierException causedBy(String message) {
		return new InvalidIdentifierException(message);
	}
}
