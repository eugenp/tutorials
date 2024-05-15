package com.baeldung;

import java.io.Serializable;

public class MyPayload implements Serializable {

	private static final long serialVersionUID = 1L;
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
