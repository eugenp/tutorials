package org.hexagonal.exception;

public class BusinessException extends Exception {
	
	public String message;
	
	public BusinessException(String message) {
		this.message = message;
	}

}
