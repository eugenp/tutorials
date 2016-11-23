package com.baeldung.constructordi.domain;

public class Transmission {
	private String type;

	public Transmission(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("%s", type);
	}
}
