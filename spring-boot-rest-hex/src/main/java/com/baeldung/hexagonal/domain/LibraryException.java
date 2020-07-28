package com.baeldung.hexagonal.domain;

public class LibraryException extends RuntimeException {
	LibraryException(final String message) {
		super(message);
	}
}
