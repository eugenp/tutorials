package com.oreilly.jebp.ejb.valueobjectproxy;

import javax.ejb.EJBException;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * This exception gets used by the ValueObjectProxy to indicate an error in
 * value object invocation.
**/
public class ValueObjectException extends EJBException {
	public ValueObjectException (String message) {
		super(message);
	} // constructor
} // ValueObjectException