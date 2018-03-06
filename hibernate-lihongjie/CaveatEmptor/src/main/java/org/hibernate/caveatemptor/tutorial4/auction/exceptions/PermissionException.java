package org.hibernate.caveatemptor.tutorial4.auction.exceptions;

/**
 * This exception is used to mark access violations.
 *
 * @author Christian Bauer
 */
public class PermissionException
	extends RuntimeException {

	public PermissionException() {}

	public PermissionException(String message) {
		super(message);
	}

	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}
}
