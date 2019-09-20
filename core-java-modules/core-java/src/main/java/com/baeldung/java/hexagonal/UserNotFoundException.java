package com.baeldung.java.hexagonal;

/**
 * A runtime exception to indicate {@link User} does not exists
 */
public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
