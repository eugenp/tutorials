package com.mala.DI.model;

public class InjectableMedicine implements IMedicine {
	private String name;
	
	public InjectableMedicine() {}

	public InjectableMedicine(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
