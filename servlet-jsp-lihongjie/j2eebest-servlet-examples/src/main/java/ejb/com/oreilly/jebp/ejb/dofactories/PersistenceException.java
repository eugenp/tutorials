package com.oreilly.jebp.ejb.dofactories;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Generic exception thrown from the domain object factories.
**/
public class PersistenceException extends Exception {
	protected Throwable rootCause = null;

	public PersistenceException (String message) {
		super(message);
	}

	public PersistenceException (Throwable cause) {
		super();
		rootCause = cause;
	}

	public PersistenceException (String message, Throwable cause) {
		super(message);
		rootCause = cause;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public String toString() {
		return super.toString() + ": " + rootCause.toString();
	}
} // PersistenceException