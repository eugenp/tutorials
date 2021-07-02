package com.baeldung.hexagonalarchinjava.domain;

class EventServiceException extends RuntimeException {

	private static final long serialVersionUID = -401473106602507463L;

	EventServiceException(final String message) {
		super(message);
	}

	EventServiceException(final String message, Throwable cause) {
		super(message, cause);
	}
}
