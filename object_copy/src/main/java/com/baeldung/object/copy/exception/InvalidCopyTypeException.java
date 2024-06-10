package com.baeldung.object.copy.exception;

public class InvalidCopyTypeException extends Exception{

	private static final long serialVersionUID = -279291701178637801L;

	@Override
	public String getMessage() {
		return "Invalid Copy Type!";
	}
}
