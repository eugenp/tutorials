package com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain;

public class Type {
	private String type;

	public Type(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("%s", type);
	}
}
