package com.mala.DI.model;

public class OralMedicine implements IMedicine { 
	private String name;
	
	public OralMedicine() {
		super();
	}
	
	public OralMedicine(String name) {
		super();
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
