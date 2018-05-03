package com.baeldung.reactive.eventstreaming;

import java.io.Serializable;

public class Event implements Serializable {

	private String message;

	public Event() {
	}

	public Event(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("Event [message = %s]", message);
	}
}
