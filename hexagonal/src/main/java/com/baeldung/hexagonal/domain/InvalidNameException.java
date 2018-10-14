package com.baeldung.hexagonal.domain;

class InvalidNameException extends PatientValidationException {

	private InvalidNameException(String message) {
		super(message);
	}

	static InvalidNameException causedBy(String message) {
		return new InvalidNameException(message);
	}
}
