package com.baeldung.hexagonal.domain;

class EmptyNamesException extends PatientValidationException {

	private EmptyNamesException(String message) {
		super(message);
	}

	static EmptyNamesException causedBy(String message) {
		return new EmptyNamesException(message);
	}
}
