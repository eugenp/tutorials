package com.baeldung.circulardependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircularDependencyB {

	private CircularDependencyA circA;

	private String message = "Hi!";

	@Autowired
	public void setCircA(final CircularDependencyA circA) {
		this.circA = circA;
	}

	public String getMessage() {
		return message;
	}

}
