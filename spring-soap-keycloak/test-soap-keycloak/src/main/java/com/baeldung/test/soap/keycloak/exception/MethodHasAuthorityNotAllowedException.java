package com.baeldung.test.soap.keycloak.exception;

public class MethodHasAuthorityNotAllowedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6538057782076982855L;

	public MethodHasAuthorityNotAllowedException(String message) {
		super(message);
	}
	
}
