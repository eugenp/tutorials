package com.baeldung;

public class MyPayload {

	private String value;

	public MyPayload(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

	public MyPayload deepClone() {
		MyPayload myPayload = new MyPayload(value);
		return myPayload;
	}
}
