package com.baeldung.typesofinjection;

public class Invoice {

	public String getNumber() {
		return "" + this.hashCode();
	}

	public String getJson() {
		return "{\"test\": \"Just a test JSON\"}";
	}

}
