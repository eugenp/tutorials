package org.baeldung.model;

public class OutputMessage {

	private String text;
	private String time;

	public OutputMessage(final String text, final String time) {
		this.text = text;
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public String getTime() {
		return time;
	}
}
