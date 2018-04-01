package com.oreilly.jebp.ejb.businessdelegate;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Business Delegates.
 *
 * Generic exception for the business delegate example.
**/
public class ApplicationException extends Exception {
	public ApplicationException (String message) {
		super(message);
	}
} // ApplicationException