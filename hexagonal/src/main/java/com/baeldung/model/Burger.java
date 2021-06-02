package com.baeldung.model;

public class Burger {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Burger [name=" + name + "]";
	}

}
