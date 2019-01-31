package com.baeldung.springboot.exception;

public class PersonException extends Exception {

	private static final long serialVersionUID = -6930542248556723784L;
	
	private String id;
	private String code;
	private String message;
		
	public PersonException() {
		super();
	}
	
	public PersonException(String id, String code, String message){
		super(message);
		this.id = id;
		this.code = code;
		this.message = message;
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
