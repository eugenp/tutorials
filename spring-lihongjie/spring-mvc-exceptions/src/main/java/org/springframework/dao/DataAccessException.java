package org.springframework.dao;

/**
 * Spring's database support defines this exception. Our demo application just
 * needs it to demonstrate how it might be handled. The definition is copied
 * here for convenience rather than pulling in the Spring JDBC dependencies.
 * 
 * @author Copied from Spring
 */
public class DataAccessException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 3620199505147655172L;

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(String msg, Throwable t) {
		super(msg, t);
	}

}
