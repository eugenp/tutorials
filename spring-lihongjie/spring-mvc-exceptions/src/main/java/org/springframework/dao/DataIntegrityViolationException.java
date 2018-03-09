package org.springframework.dao;

/**
 * Spring's database support defines this exception. Our demo application just
 * needs it to demonstrate how it might be handled. The definition is copied
 * here for convenience rather than pulling in the Spring JDBC dependencies.
 * 
 * @author Copied from Spring
 */
public class DataIntegrityViolationException extends DataAccessException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = -8146834359701827537L;

	public DataIntegrityViolationException(String msg) {
		super(msg);
	}

	public DataIntegrityViolationException(String msg, Throwable t) {
		super(msg, t);
	}

}
