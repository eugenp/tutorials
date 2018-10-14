package com.baeldung.hexagonal.domain;

public abstract class PatientValidationException extends RuntimeException {

	PatientValidationException(String message) {
		super(message);
	}
}
